package com.hung.junit.integration.service;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@TransactionConfiguration
@Transactional
public class DomainServiceTest  {

    private static Logger log = Logger.getLogger(DomainServiceTest.class);
    
    /*
    @Autowired
    private DomainService domainService;    // class under-test, this instance will be dependency injected by type
    */
    
    @Autowired
    @Qualifier("domainService")
    private DomainService domainService;    // this instance will be dependency injected by name
    
    @Before
    public void setUp() {  
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testQueryRoot() {
        log.info("////////// DomainServiceTest : testQueryRoot - start //////////");
        
        String expectedDomainName = Domain.ROOT_NAME;
        String actualDomainName = domainService.findByName(Domain.ROOT_NAME).getName();
        log.info("expectedDomainName="+expectedDomainName+" VS "+"actualDomainName="+actualDomainName);
        
        Assert.assertEquals(expectedDomainName, actualDomainName);
        
        log.info("////////// DomainServiceTest : testQueryRoot - end //////////");
    }
    
    @Test
    public void testSave() {
        log.info("////////// DomainServiceTest:testQueryRoot : testSave - start //////////");
        
        Domain testTransactionalSpringDomain = new Domain("testTransactionalSpringDomain", "testTransactionalSpringDomain");
        domainService.save(testTransactionalSpringDomain);
        
        Domain refTestTransactionalSpringDomain = domainService.findByName("testTransactionalSpringDomain");
        
        String expectedDomainName = testTransactionalSpringDomain.getName();
        String actualDomainName = refTestTransactionalSpringDomain.getName();
        Assert.assertEquals(expectedDomainName, actualDomainName);
        
        log.info("////////// DomainServiceTest:testQueryRoot : testSave - end //////////");
    }
}
