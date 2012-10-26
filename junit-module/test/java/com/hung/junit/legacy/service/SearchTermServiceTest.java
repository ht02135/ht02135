package com.hung.junit.legacy.service;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.client.ClientSearchResult;
import com.hung.auction.service.SearchTermService;

public class SearchTermServiceTest extends TestCase {
	
	private static Logger log = Logger.getLogger(SearchTermServiceTest.class);
	
	private ApplicationContext applicationContext = null;
	private SessionFactory sessionFactory = null;
	private SearchTermService searchTermService = null;	
	
	public void testDummy() {}
	
	/*
	protected void setUp() {
		log.info("********** setUp: enter **********");
		applicationContext = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		searchTermService = (SearchTermService) applicationContext.getBean("searchTermService");
		log.info("********** setUp: exit **********");
	}
	
	public void testSearchTerm() {
		ClientSearchResult clientSearchResult = searchTermService.findByTerm("Hung");
		try {
			if (clientSearchResult.getStatus().equalsIgnoreCase(ClientSearchResult.BUSY_STATUS)) {
				log.info("busy searching Hung");
				Thread.sleep(1000);
			} else {
				log.info("clientSearchResult.getDocuments()="+clientSearchResult.getDocuments());
			}
		} catch (Exception e) {}
		
		clientSearchResult = searchTermService.findByTerm("Hung");
		try {
			if (clientSearchResult.getStatus().equalsIgnoreCase(ClientSearchResult.BUSY_STATUS)) {
				log.info("busy searching Hung");
				Thread.sleep(1000);
			} else {
				log.info("clientSearchResult.getDocuments()="+clientSearchResult.getDocuments());
			}
		} catch (Exception e) {}
		
		clientSearchResult = searchTermService.findByTerm("POJO");
		try {
			if (clientSearchResult.getStatus().equalsIgnoreCase(ClientSearchResult.BUSY_STATUS)) {
				log.info("busy searching POJO");
				Thread.sleep(1000);
			} else {
				log.info("clientSearchResult.getDocuments()="+clientSearchResult.getDocuments());
			}
		} catch (Exception e) {}
		
		clientSearchResult = searchTermService.findByTerm("download");
		try {
			if (clientSearchResult.getStatus().equalsIgnoreCase(ClientSearchResult.BUSY_STATUS)) {
				log.info("busy searching download");
				Thread.sleep(1000);
			} else {
				log.info("clientSearchResult.getDocuments()="+clientSearchResult.getDocuments());
			}
		} catch (Exception e) {}
		
		clientSearchResult = searchTermService.findByTerm("Connectivity");
		try {
			if (clientSearchResult.getStatus().equalsIgnoreCase(ClientSearchResult.BUSY_STATUS)) {
				log.info("busy searching Connectivity");
				Thread.sleep(1000);
			} else {
				log.info("clientSearchResult.getDocuments()="+clientSearchResult.getDocuments());
			}
		} catch (Exception e) {}
		
		clientSearchResult = searchTermService.findByTerm("SpringMVC");
		try {
			if (clientSearchResult.getStatus().equalsIgnoreCase(ClientSearchResult.BUSY_STATUS)) {
				log.info("busy searching SpringMVC");
				Thread.sleep(1000);
			} else {
				log.info("clientSearchResult.getDocuments()="+clientSearchResult.getDocuments());
			}
		} catch (Exception e) {}
	}
	*/
}
