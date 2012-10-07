package com.hung.dao;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.DomainDAO;
import com.hung.auction.domain.Domain;

// http://static.springsource.org/spring/docs/3.0.x/reference/testing.html
// http://static.springsource.org/spring/docs/3.0.0.M3/reference/html/ch10s03.html

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@TransactionConfiguration
@Transactional
public class DomainDaoTransactionTest extends AbstractTransactionalJUnit4SpringContextTests {

    private static Logger log = Logger.getLogger(DomainDaoTransactionTest.class);
    
    // this instance will be dependency injected by type
    @Autowired
    private DomainDAO domainDAO2;
    
    // this instance will be dependency injected by name
    @Resource
    private DomainDAO domainDAO;
    
    @Before
    public void before() {
    }
    
    @After
    public void after() {
    }
    
    @BeforeTransaction
    public void beforeTransaction() {
        // logic to verify the initial state before a transaction is started
    }
    
    /*
    You will occasionally find that you need to execute certain code before or after a transactional test method 
    but outside the transactional context, for example to verify the initial database state prior to execution of 
    your test or to verify expected transactional commit behavior after test execution

    annotated public void method should be executed after a transaction is ended for test methods 
    configured to run within a transaction via the @Transactional annotation
    */
    @AfterTransaction
    public void afterTransaction() {
        // logic to verify the final state after transaction has rolled back
    }

    @Test
    public void testSave() {
        log.info("////////// DomainDaoTransactionTest : testSave - start //////////");
        
        Domain testTransactionalSpringDomain = new Domain("testTransactionalSpringDomain", "testTransactionalSpringDomain");
        domainDAO.save(testTransactionalSpringDomain);
        
        Domain refTestTransactionalSpringDomain = domainDAO.findByName("testTransactionalSpringDomain");
        
        String expectedDomainName = testTransactionalSpringDomain.getName();
        String actualDomainName = refTestTransactionalSpringDomain.getName();
        Assert.assertEquals(expectedDomainName, actualDomainName);
        
        log.info("////////// DomainDaoTransactionTest : testSave - end //////////");
    }
}
