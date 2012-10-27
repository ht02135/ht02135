package com.hung.junit.integration.dao;

import java.util.List;

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

import com.hung.auction.dao.DomainDAO;
import com.hung.auction.domain.Domain;
import com.hung.junit.fixture.DBUnitFixture;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
public class DomainDaoDBUnitTest extends DBUnitFixture {

    private static Logger log = Logger.getLogger(DomainDaoDBUnitTest.class);
    
    // this instance will be dependency injected by name
    @Autowired
    @Qualifier("domainDAO")
    private DomainDAO domainDAO;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testFindAll() {
        List<Domain> domans = domainDAO.findAll();
    }
    
    @Test
    public void testSave() {
        log.info("////////// DomainDaoTransactionTest : testSave - start //////////");
        
        Domain testSaveDomain = new Domain("testSave", "root");
        domainDAO.save(testSaveDomain);
        
        Domain refTestSaveDomain = domainDAO.findByName("testSave");
        
        String expectedDomainName = testSaveDomain.getName();
        String actualDomainName = refTestSaveDomain.getName();
        Assert.assertEquals(expectedDomainName, actualDomainName);
        
        log.info("////////// DomainDaoTransactionTest : testSave - end //////////");
    }
}
