package com.hung.auction.service;

import com.hung.auction.domain.StringDocument;
import com.hung.auction.domain.Term;

public interface BuildIndexService {
	
    public void buildIndexForTerm(String term);
    
    public void buildIndexForTerm(Term term);
    
    public void buildIndexForDocument(StringDocument document);
    
    public void deleteIndexForDocument(StringDocument document);
}