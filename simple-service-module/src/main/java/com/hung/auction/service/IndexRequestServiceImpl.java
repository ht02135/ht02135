package com.hung.auction.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.IndexRequestDAO;
import com.hung.auction.domain.AbstractIndexRequest;
import com.hung.auction.domain.DocumentIndexRequest;
import com.hung.auction.domain.TermIndexRequest;

@Service("indexRequestService")
public class IndexRequestServiceImpl implements IndexRequestService {

    private static Logger log = Logger.getLogger(IndexRequestServiceImpl.class);

    @Autowired
    @Qualifier("indexRequestDAO")
    private IndexRequestDAO indexRequestDAO;

    public IndexRequestServiceImpl() {
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void save(AbstractIndexRequest indexRequest) {
        log.info("save: indexRequest="+indexRequest);
        indexRequestDAO.save(indexRequest);
        log.info("save: after save - indexRequest="+indexRequest);
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public TermIndexRequest findByTermId(String termId, String status) {
        TermIndexRequest termIndexRequest = null;
        try {
            termIndexRequest =  indexRequestDAO.findByTermId(termId, status);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return termIndexRequest;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public DocumentIndexRequest findByDocumentId(Integer documentId, String status) {
        DocumentIndexRequest documentIndexRequest = null;
        try {
            documentIndexRequest =  indexRequestDAO.findByDocumentId(documentId, status);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return documentIndexRequest;
    }
    
    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public AbstractIndexRequest findById(final Integer id, final String status) {
        log.info("findById : id="+id+" status="+status);
        AbstractIndexRequest abstractIndexRequest = null;
        try {
            abstractIndexRequest =  indexRequestDAO.findById(id, status);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return abstractIndexRequest;
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<AbstractIndexRequest> findAll(String status) {
        return indexRequestDAO.findAll(status);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void deleteById(Integer id) {
        log.info("deleteById : id="+id);
        if (findById(id, AbstractIndexRequest.PROCESSED_STATUS) != null) {
            indexRequestDAO.deleteById(id);
        }
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void softDeleteById(Integer id) {
        log.info("softDeleteById : id="+id);
        if (findById(id, AbstractIndexRequest.OPEN_STATUS) != null) {
            indexRequestDAO.softDeleteById(id);
        }
    }
}