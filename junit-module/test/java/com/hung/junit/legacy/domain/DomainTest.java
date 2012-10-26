package com.hung.junit.legacy.domain;

import java.util.HashSet;
import java.util.Hashtable;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.hung.auction.domain.Domain;

public class DomainTest extends TestCase {
	
	private static Logger log = Logger.getLogger(DomainTest.class);
	
	public void testDummy() {}
	
	/*
	protected void setUp() {
	}
	
	// Set definitely call equals() and hashCode()
	public void testHashSet() {
		log.info("********** testHashSet: start **********");
		
		HashSet domainSet = new HashSet();
		domainSet.add(new Domain("domain1"));
		domainSet.add(new Domain("domain2"));
		domainSet.add(new Domain("domain3"));
		domainSet.add(new Domain("domain4"));
		domainSet.add(new Domain("domain5"));
		
		Domain domain6 = new Domain("domain6");
		domainSet.add(domain6);
		domainSet.add(domain6);
		domainSet.add(domain6);
		
		domainSet.add(new Domain("domain1"));
		domainSet.add(new Domain("domain2"));
		
		domainSet.add(null);	// omg, you can add null to HashSet
		domainSet.add(null);	// omg, you can add null to HashSet
		
		log.info("testHashSet: domainSet.size()="+domainSet.size());
		
		domainSet.contains(domain6);
		domainSet.contains(new Domain("domain1"));
		
		int expectedSize = 7;
		int actualSize = domainSet.size();
		Assert.assertEquals(expectedSize, actualSize);
		
		log.info("********** testHashSet: end **********");
	}
	
	// Table doesnt call equals() and hashCode()
	public void testHashTable() {
		log.info("********** testHashTable: start **********");
		
		Hashtable domainTable = new Hashtable();
		
		Domain domain1 = new Domain("domain1");
		Domain domain2 = new Domain("domain2");
		Domain domain3 = new Domain("domain3");
		Domain domain4 = new Domain("domain4");
		Domain domain5 = new Domain("domain5");
		Domain domain6 = new Domain("domain6");
		
		domainTable.put("domain1", domain1);
		domainTable.put("domain2", domain2);
		domainTable.put("domain3", domain3);
		domainTable.put("domain4", domain4);
		domainTable.put("domain5", domain5);
		
		domainTable.put("domain6", domain6);
		domainTable.put("domain6", domain6);
		
		domainTable.put("domain1", new Domain("domain1"));
		domainTable.put("domain2", new Domain("domain2"));
		
		log.info("testHashTable: domainTable.size()="+domainTable.size());
		
		domainTable.get("domain6");
		domainTable.get("domain1");
		
		int expectedSize = 6;
		int actualSize = domainTable.size();
		Assert.assertEquals(expectedSize, actualSize);
		
		log.info("********** testHashTable: end **********");
	}
	*/
}
