package com.hung.junit.legacy.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.hung.auction.jaxbdomain.JaxbDomain;

public class DomainComparatorTest extends TestCase {
	
	private static Logger log = Logger.getLogger(DomainComparatorTest.class);
	
	public void testDummy() {}
	
	/*
	protected void setUp() {
	}
	
	// Set definitely call equals() and hashCode()
	public void testComparator() {
		log.info("********** testComparator: start **********");
		
		List<JaxbDomain> domainList = new ArrayList<JaxbDomain>();
		
		log.info("testComparator: creating domains");
		JaxbDomain domain1 = new JaxbDomain("domain1", "domain6");
		JaxbDomain domain2 = new JaxbDomain("domain2", "domain5");
		JaxbDomain domain3 = new JaxbDomain("domain3", "domain4");
		JaxbDomain domain4 = new JaxbDomain("domain4", "domain3");
		JaxbDomain domain5 = new JaxbDomain("domain5", "domain2");
		JaxbDomain domain6 = new JaxbDomain("domain6", "domain1");

		log.info("testComparator: adding domains to list");
		domainList.add(domain1);
		domainList.add(domain4);
		domainList.add(domain5);
		domainList.add(domain2);
		domainList.add(domain3);	
		domainList.add(domain6);
		
		log.info("testComparator: done list setup");
		
		log.info("testComparator: domainList="+domainList);
		
		// sort by parent name
		log.info("testComparator: sort by Domain.PARENT_NAME_COMPARATOR");
		Collections.sort(domainList, JaxbDomain.PARENT_NAME_COMPARATOR);
		log.info("testComparator: after sorted by Domain.PARENT_NAME_COMPARATOR, domainList="+domainList);
		
		log.info("********** testComparator: end **********");
	}
	*/
}
