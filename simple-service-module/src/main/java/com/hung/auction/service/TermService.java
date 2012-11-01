package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.Term;

public interface TermService {

    public void save(Term term);
    
    public Term findById(String id);
    
    public List<Term> findAll();
}