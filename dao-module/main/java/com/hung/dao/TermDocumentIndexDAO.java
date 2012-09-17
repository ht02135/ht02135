package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.TermDocumentIndex;

public interface TermDocumentIndexDAO {

    public void save(TermDocumentIndex termDocumentIndex);
    
    public TermDocumentIndex findById(Integer id);
    
    public List<TermDocumentIndex> findByTermId(String termId);
    
    public List<TermDocumentIndex> findByDocumentId(Integer documentId);
    
    public void deleteById(Integer id);
}