package com.hung.auction.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.client.ClientSearchResult;
import com.hung.auction.domain.AbstractIndexRequest;
import com.hung.auction.domain.StringDocument;
import com.hung.auction.domain.TermDocumentIndex;
import com.hung.auction.domain.TermIndexRequest;

@Service("searchTermService")
public class SearchTermServiceImpl implements SearchTermService {

    private static Logger log = Logger.getLogger(SearchTermServiceImpl.class);

    @Autowired
    @Qualifier("termService")
    private TermService termService;

    @Autowired
    @Qualifier("termDocumentIndexService")
    private TermDocumentIndexService termDocumentIndexService;

    @Autowired
    @Qualifier("indexRequestService")
    private IndexRequestService indexRequestService;

    @Autowired
    @Qualifier("buildIndexService")
    private BuildIndexService buildIndexService;

    public SearchTermServiceImpl() {
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public ClientSearchResult findByTerm(String term) {
        log.info("findByTerm: term="+term);

        ClientSearchResult clientSearchResult = new ClientSearchResult(ClientSearchResult.BUSY_STATUS);
        List<StringDocument> documents = Collections.EMPTY_LIST;

        // check if term exist in termService
        if (termService.findById(term) != null) {
            log.info("findByTerm: found term="+term);

            TermIndexRequest termIndexRequest = indexRequestService.findByTermId(term, AbstractIndexRequest.OPEN_STATUS);
            if (termIndexRequest == null) {
                log.info("findByTerm: no pending request for term="+term);

                // get index from termDocumentIndexService
                List<TermDocumentIndex> indexes = termDocumentIndexService.findByTermId(term);
                documents = new ArrayList<StringDocument>(indexes.size());
                for (int i=0;i<indexes.size();i++) {
                    documents.add((StringDocument) indexes.get(i).getDocument());
                }

                clientSearchResult = new ClientSearchResult(documents, ClientSearchResult.OK_STATUS);
            } else {
                log.info("findByTerm: there is pending request for term="+term+" termIndexRequest="+termIndexRequest);
            }
        } else {
            log.info("findByTerm: need to insert not found term="+term);

            // build index
            log.info("findByTerm: need to build for term="+term);
            buildIndexService.buildIndexForTerm(term);

            clientSearchResult = new ClientSearchResult(ClientSearchResult.BUSY_STATUS);
        }
        return clientSearchResult;
    }
}