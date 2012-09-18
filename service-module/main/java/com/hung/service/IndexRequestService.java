package com.hung.auction.service;

import java.util.List;

import com.hung.auction.domain.AbstractIndexRequest;
import com.hung.auction.domain.DocumentIndexRequest;
import com.hung.auction.domain.TermIndexRequest;

public interface IndexRequestService {

    public void save(AbstractIndexRequest indexRequest);

    public TermIndexRequest findByTermId(String termId, String status);

    public DocumentIndexRequest findByDocumentId(Integer documentId, String status);

    public List<AbstractIndexRequest> findAll(String status);

    public void deleteById(Integer id);

    public void softDeleteById(Integer id);
}