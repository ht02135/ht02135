package com.hung.junit.legacy.domain;

import java.util.HashSet;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.hung.auction.jaxbdomain.JaxbDomain;

public class DomainToJSAONTest extends TestCase {
	
	private static Logger log = Logger.getLogger(DomainToJSAONTest.class);
	
	private Gson gson = null;
	
	public void testDummy() {}
	
	/*
	protected void setUp() {
		gson = new Gson();
	}
	
	// Set definitely call equals() and hashCode()
	public void testJaxbDomainToJSON() {
		log.info("********** testJaxbDomainToJSON: start **********");
		
		JaxbDomain domain1 = new JaxbDomain("domain1", "root");
		JaxbDomain domain2 = new JaxbDomain("domain2", "root");
		JaxbDomain domain3 = new JaxbDomain("domain3", "root");
		
		HashSet<JaxbDomain> domainSet = new HashSet<JaxbDomain>();
		domainSet.add(domain1);
		domainSet.add(domain2);
		domainSet.add(domain3);
		
		String jsonDomain1 = gson.toJson(domain1);
		String jsonDomain2 = gson.toJson(domain2);
		String jsonDomain3 = gson.toJson(domain3);
		
		log.info("testJaxbDomainToJSON: jsonDomain1="+jsonDomain1);
		log.info("testJaxbDomainToJSON: jsonDomain2="+jsonDomain2);
		log.info("testJaxbDomainToJSON: jsonDomain3="+jsonDomain3);
		
		log.info("********** testJaxbDomainToJSON: end **********");
	}
    */
}
