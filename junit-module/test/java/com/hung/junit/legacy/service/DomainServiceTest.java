package com.hung.junit.legacy.service;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

public class DomainServiceTest extends TestCase {
	
	private static Logger log = Logger.getLogger(DomainServiceTest.class);
	
	private ApplicationContext applicationContext = null;
	private SessionFactory sessionFactory = null;
	private DomainService domainService = null;	
	
	public void testDummy() {}
	
	/*
	protected void setUp() {
		log.info("********** setUp: enter **********");
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		domainService = (DomainService) applicationContext.getBean("domainService");
		log.info("********** setUp: exit **********");
	}
	
	public void testGetNamesByPattern() {
		log.info("********** testGetNamesByPattern: enter **********");
		
		log.info("getNamesByPatternStoreProcedure: start >>>>>>>>>>");
		List<String> names = domainService.getNamesByPatternStoreProcedure("testDomain");
		log.info("testGetNamesByPattern: called getNamesByPatternStoreProcedure, names="+names);
		log.info("getNamesByPatternStoreProcedure: end <<<<<<<<<<");
		log.info("");
		
		log.info("****************************************");
		log.info("getNamesByPatternSQL: start >>>>>>>>>>");
		names = domainService.getNamesByPatternSQL("testDomain");
		log.info("testGetNamesByPattern: called getNamesByPatternSQL, names="+names);
		log.info("getNamesByPatternSQL: end <<<<<<<<<<");
		log.info("");
		
		log.info("****************************************");
		log.info("getParentNamesByPatternSQL: start >>>>>>>>>>");
		names = domainService.getParentNamesByPatternSQL("testDomain");
		log.info("testGetNamesByPattern: called getParentNamesByPatternSQL, names="+names);
		log.info("getParentNamesByPatternSQL: end <<<<<<<<<<");
		log.info("");
		
		log.info("****************************************");
		log.info("getDomainsByPatternStoreProcedure: start >>>>>>>>>>");
		List<Domain> domains = domainService.getDomainsByPatternStoreProcedure("testDomain");
		log.info("testGetNamesByPattern: called getDomainsByPatternStoreProcedure, domains="+domains);
		log.info("getDomainsByPatternStoreProcedure: end <<<<<<<<<<");
		log.info("");
		
		log.info("****************************************");
		log.info("getDomainsByPatternSQL: start >>>>>>>>>>");
		domains = domainService.getDomainsByPatternSQL("testDomain");
		log.info("testGetNamesByPattern: called getDomainsByPatternSQL, domains="+domains);
		log.info("getDomainsByPatternSQL: end <<<<<<<<<<");
		log.info("");
		
		log.info("****************************************");
		log.info("getDomainsByFieldsPatternSQL: start >>>>>>>>>>");
		domains = domainService.getDomainsByFieldsPatternSQL("testDomain");
		log.info("testGetNamesByPattern: called getDomainsByFieldsPatternSQL, domains="+domains);
		log.info("getDomainsByFieldsPatternSQL: end <<<<<<<<<<");
		log.info("");
		
		log.info("****************************************");
		log.info("getDomainsByPatternHQL: start >>>>>>>>>>");
		domains = domainService.getDomainsByPatternHQL("testDomain");
		log.info("testGetNamesByPattern: called getDomainsByPatternHQL, domains="+domains);
		log.info("getDomainsByPatternHQL: end <<<<<<<<<<");
		log.info("");
	    
		log.info("********** testGetNamesByPattern: exit **********");
	}
	*/
}
