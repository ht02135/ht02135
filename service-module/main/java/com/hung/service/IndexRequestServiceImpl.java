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
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public TermIndexRequest findByTermId(String termId, String status) {
        return indexRequestDAO.findByTermId(termId, status);
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public DocumentIndexRequest findByDocumentId(Integer documentId, String status) {
        return indexRequestDAO.findByDocumentId(documentId, status);
    }

    @Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<AbstractIndexRequest> findAll(String status) {
        return indexRequestDAO.findAll(status);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void deleteById(Integer id) {
        indexRequestDAO.deleteById(id);
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public void softDeleteById(Integer id) {
        indexRequestDAO.softDeleteById(id);
    }
}