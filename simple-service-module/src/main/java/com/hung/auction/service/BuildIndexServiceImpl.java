package com.hung.auction.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.domain.DocumentIndexRequest;
import com.hung.auction.domain.StringDocument;
import com.hung.auction.domain.Term;
import com.hung.auction.domain.TermIndexRequest;
import com.hung.auction.engine.BuildIndexEngine;

@Service("buildIndexService")
public class BuildIndexServiceImpl implements BuildIndexService {
    
	private static Logger log = Logger.getLogger(BuildIndexServiceImpl.class);
	
	// @Autowired tells Spring to find a bean of the declared type and wire in that bean
	// @Autowired annotation to auto wire bean on the setter method, constructor or a field
	
	@Autowired
	@Qualifier("termService")	
	private TermService termService;
	
	@Autowired
	@Qualifier("indexRequestService")	
	private IndexRequestService indexRequestService;
	
	private BuildIndexEngine buildIndexEngine;
	
	@Transactional(propagation=Propagation.REQUIRED)
    public void buildIndexForTerm(String term) {
    	log.info("buildIndexForTerm: term="+term);
    	
    	// create and add index request to indexRequestService
    	TermIndexRequest indexRequest = buildIndexEngine.createTermIndexRequest(term);
    	
    	// tell buildIndexEngine to handle index request (buildIndexEngine needs to
    	// delete index request when is done
    	buildIndexEngine.handleIndexRequest(indexRequest);
    }
	
	@Transactional(propagation=Propagation.REQUIRED)
    public void buildIndexForTerm(Term term) {
    	log.info("buildIndexForTerm: term="+term);
    	
    	// create and add index request to indexRequestService
    	TermIndexRequest indexRequest = buildIndexEngine.createTermIndexRequest(term);
    	
    	// tell buildIndexEngine to handle index request (buildIndexEngine needs to
    	// delete index request when is done
    	buildIndexEngine.handleIndexRequest(indexRequest);
    }
    
	@Transactional(propagation=Propagation.REQUIRED)
    public void buildIndexForDocument(StringDocument document) {
    	log.info("buildIndexForDocument: document="+document);
    	
    	// create and add index request to indexRequestService
    	DocumentIndexRequest indexRequest = buildIndexEngine.createDocumentIndexRequest(document, DocumentIndexRequest.ADD_DOCUMENT_INDEX);
    	
    	// tell buildIndexEngine to handle index request (buildIndexEngine needs to
    	// delete index request when is done
    	log.info("buildIndexForDocument: before handleDocumentIndexRequest() indexRequest="+indexRequest);
    	if (buildIndexEngine != null) {
    		buildIndexEngine.handleIndexRequest(indexRequest);
    	} else {
    		log.info("buildIndexForDocument: buildIndexEngine is null");
    	}
    }
    
	@Transactional(propagation=Propagation.REQUIRED)
    public void deleteIndexForDocument(StringDocument document) {
    	log.info("deleteIndexForDocument: document="+document);
    	
    	// create and add index request to indexRequestService
    	DocumentIndexRequest indexRequest = buildIndexEngine.createDocumentIndexRequest(document, DocumentIndexRequest.DELETE_DOCUMENT_INDEX);
    	
    	// tell buildIndexEngine to handle index request (buildIndexEngine needs to
    	// delete index request when is done
    	log.info("deleteIndexForDocument: before handleDocumentIndexRequest() indexRequest="+indexRequest);
    	if (buildIndexEngine != null) {
    		buildIndexEngine.handleIndexRequest(indexRequest);
    	} else {
    		log.info("deleteIndexForDocument: buildIndexEngine is null");
    	}
    }
    
    // ------------------------------------------------
    
	@Autowired
    public void setBuildIndexEngine(@Qualifier("buildIndexEngine") BuildIndexEngine buildIndexEngine) { 
    	this.buildIndexEngine = buildIndexEngine; 
    }
}