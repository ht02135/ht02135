package com.hung.auction.service;

import com.hung.auction.client.ClientSearchResult;

public interface SearchTermService {
    
    public ClientSearchResult findByTerm(String term);
}