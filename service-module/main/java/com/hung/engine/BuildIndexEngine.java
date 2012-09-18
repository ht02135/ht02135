package com.hung.auction.engine;

import com.hung.auction.domain.DocumentIndexRequest;
import com.hung.auction.domain.StringDocument;
import com.hung.auction.domain.Term;
import com.hung.auction.domain.TermIndexRequest;

public interface BuildIndexEngine {

    public TermIndexRequest createTermIndexRequest(String term);

    public TermIndexRequest createTermIndexRequest(Term term);

    public DocumentIndexRequest createDocumentIndexRequest(StringDocument document, String documentAction);

    public void handleIndexRequest(TermIndexRequest indexRequest);

    public void handleIndexRequest(DocumentIndexRequest indexRequest);
}