package com.hung.auction.service;

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
    public AbstractIndexRequest findById(Integer id) {
    	return indexRequestDAO.findById(id);
    }
    
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public TermIndexRequest findByTermId(String termId) {
    	return indexRequestDAO.findByTermId(termId);
    }
    
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public DocumentIndexRequest findByDocumentId(Integer documentId) {
    	return indexRequestDAO.findByDocumentId(documentId);
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public void deleteById(Integer id) {
    	log.info("***********************");
    	log.info("* deleteById: id="+id+" *");
    	log.info("***********************");
    	
    	indexRequestDAO.deleteById(id);
    }
}