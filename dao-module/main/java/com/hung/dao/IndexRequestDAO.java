package com.hung.auction.dao;

import com.hung.auction.domain.AbstractIndexRequest;
import com.hung.auction.domain.DocumentIndexRequest;
import com.hung.auction.domain.TermIndexRequest;

public interface IndexRequestDAO {

    public void save(AbstractIndexRequest indexRequest);

    public AbstractIndexRequest findById(Integer id);

    public TermIndexRequest findByTermId(String termId);

    public DocumentIndexRequest findByDocumentId(Integer documentId);

    public void deleteById(Integer id);
}