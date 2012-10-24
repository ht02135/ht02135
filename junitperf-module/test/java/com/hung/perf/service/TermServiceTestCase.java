package com.hung.perf.service;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.domain.Term;
import com.hung.auction.service.TermService;

public class TermServiceTestCase extends TestCase {
	
	private static Logger log = Logger.getLogger(TermServiceTestCase.class);
    
    @Autowired
    @Qualifier("termService")
    private TermService termService;
    
    public TermServiceTestCase(String name) {
        super(name);
    }
    
    public void setUp() {  
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-test.xml");
        termService = (TermService) applicationContext.getBean("termService");
    }
    
    public void testTermService() {
        log.info("////////// SearchTermServiceTest : testTermService - start //////////");
        
        Term refTerm = termService.findById("testTermService");
        log.info("refTerm="+refTerm);
        Assert.assertNull(refTerm);
        
        log.info("////////// SearchTermServiceTest : testTermService - end //////////");
    }
    
    public void testDummy() {
        log.info("////////// SearchTermServiceTest : testDummy - start //////////");
        
        Assert.assertEquals(true, true);
        
        log.info("////////// SearchTermServiceTest : testDummy - end //////////");
    }
}
