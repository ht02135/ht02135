package com.hung.junit.integration.service;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.client.ClientSearchResult;
import com.hung.auction.domain.Term;
import com.hung.auction.service.SearchTermService;
import com.hung.auction.service.TermService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@TransactionConfiguration
@Transactional
public class SearchTermServiceTest {
	
	private static Logger log = Logger.getLogger(SearchTermServiceTest.class);
	
    @Autowired
    @Qualifier("searchTermService")
	private SearchTermService searchTermService;	
    
    @Autowired
    @Qualifier("termService")
    private TermService termService;
	
    @Before
    public void setUp() {  
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testTermService() {
        log.info("////////// SearchTermServiceTest : testTermService - start //////////");
        
        Term term = new Term("testTermService");
        termService.save(term);
        Term refTerm = termService.findById("testTermService");
        Assert.assertNotNull(refTerm);
        
        log.info("////////// SearchTermServiceTest : testTermService - end //////////");
    }
	
    @Test
	public void testSearchTerm() {
        log.info("////////// SearchTermServiceTest : testSearchTerm - start //////////");
        
        // search a unlikely searched term
        log.info("1st term serach");
		ClientSearchResult clientSearchResult = searchTermService.findByTerm("SearchTermServiceTest");
		
        String expectedStatus = ClientSearchResult.BUSY_STATUS;
        String actualStatus = clientSearchResult.getStatus();
        log.info("expectedStatus="+expectedStatus+" VS "+" actualStatus="+actualStatus);
        Assert.assertEquals(expectedStatus, actualStatus);  
        log.info("passed status check");
		
		log.info("////////// SearchTermServiceTest : testSearchTerm - end //////////");
	}
}
