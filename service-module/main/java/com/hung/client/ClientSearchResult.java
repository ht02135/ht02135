package com.hung.auction.client;

import java.io.Serializable;
import java.util.List;

import com.hung.auction.domain.StringDocument;

public class ClientSearchResult implements Serializable {

    public static final String OK_STATUS = "OK";
    public static final String BUSY_STATUS = "BUSY";

    private List<StringDocument> documents;
    private String status;

    public ClientSearchResult() {
    }

    public ClientSearchResult(String status) {
        setStatus(status);
    }

    public ClientSearchResult(List<StringDocument> documents, String status) {
        setDocuments(documents);
        setStatus(status);
    }

    public List<StringDocument> getDocuments() {
        return documents;
    }
    public void setDocuments(List<StringDocument> documents) {
        this.documents = documents;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isBusy() {
        return (getStatus().equalsIgnoreCase(ClientSearchResult.BUSY_STATUS));
    }

    public String toString() {
        return "[documents="+documents+", status="+status+"]";
    }
}

