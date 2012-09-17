package com.hung.auction.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.TermDAO;
import com.hung.auction.domain.Term;

@Service("termService")
public class TermServiceImpl implements TermService {
	
	private static Logger log = Logger.getLogger(TermServiceImpl.class);
	
	@Autowired
	@Qualifier("termDAO")	
	private TermDAO termDAO;
	
	public TermServiceImpl() {
	}

	@Transactional(propagation=Propagation.REQUIRED)
    public void save(Term term) {
    	termDAO.save(term);
    }
    
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public Term findById(String id) {
    	return termDAO.findById(id);
    }
    
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
    public List<Term> findAll() {
    	return termDAO.findAll();
    }
}