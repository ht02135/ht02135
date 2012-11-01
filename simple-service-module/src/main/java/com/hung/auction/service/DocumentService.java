package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.AbstractDocument;
import com.hung.auction.domain.StringDocument;

public interface DocumentService {

    public void save(AbstractDocument document);

    public AbstractDocument findById(Integer id);

    public List<StringDocument> findByTerm(String term);

    public List<AbstractDocument> findAll();

    public List<StringDocument> findStringDocuments();

    public void deleteById(Integer id);
}
