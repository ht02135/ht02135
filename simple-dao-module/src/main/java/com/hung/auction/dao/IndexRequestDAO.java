package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.AbstractIndexRequest;
import com.hung.auction.domain.DocumentIndexRequest;
import com.hung.auction.domain.TermIndexRequest;

public interface IndexRequestDAO {

    public void save(AbstractIndexRequest indexRequest);

    public AbstractIndexRequest findById(Integer id, String status);

    public TermIndexRequest findByTermId(String termId, String status);

    public DocumentIndexRequest findByDocumentId(Integer documentId, String status);

    public List<AbstractIndexRequest> findAll(String status);

    public void deleteById(Integer id);

    public void softDeleteById(Integer id);
}