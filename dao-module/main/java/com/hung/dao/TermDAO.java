package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.Term;

public interface TermDAO {

    public void save(Term term);
    
    public Term findById(String id);
    
    public List<Term> findAll();
}