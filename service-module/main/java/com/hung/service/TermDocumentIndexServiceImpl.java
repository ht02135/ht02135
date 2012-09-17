package com.hung.auction.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.TermDocumentIndexDAO;
import com.hung.auction.domain.TermDocumentIndex;

@Service("termDocumentIndexService")
public class TermDocumentIndexServiceImpl implements TermDocumentIndexService {
	
	private static Logger log = Logger.getLogger(TermDocumentIndexServiceImpl.class);
	
	@Autowired
	@Qualifier("termDocumentIndexDAO")	
	private TermDocumentIndexDAO termDocumentIndexDAO;
	
	public TermDocumentIndexServiceImpl() {
	}

	@Transactional(propagation=Propagation.REQUIRED)
    public void save(TermDocumentIndex termDocumentIndex) {
    	termDocumentIndexDAO.save(termDocumentIndex);
    }
    
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public TermDocumentIndex findById(Integer id) {
    	return termDocumentIndexDAO.findById(id);
    }
    
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<TermDocumentIndex> findByTermId(String termId) {
    	return termDocumentIndexDAO.findByTermId(termId);
    }
    
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<TermDocumentIndex> findByDocumentId(Integer documentId) {
    	return termDocumentIndexDAO.findByDocumentId(documentId);
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public void deleteById(Integer id) {
    	termDocumentIndexDAO.deleteById(id);
    }
}