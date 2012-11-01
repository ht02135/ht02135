package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.AbstractDocument;
import com.hung.auction.domain.StringDocument;

public interface DocumentDAO {

    public void save(AbstractDocument document);

    public AbstractDocument findById(Integer id);

    public List<AbstractDocument> findAll();

    public List<StringDocument> findStringDocuments();

    public List<StringDocument> findByTerm(String term);

    // for simplicity, use text, complex use some searchCriteria abstraction
    public List<StringDocument> findBySearchText(String searchText);

    public List<StringDocument> findBySearchTextSQL(String searchText);

    public void deleteById(Integer id);
}