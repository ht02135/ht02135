package com.hung.junit.unit.service;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
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

import com.hung.auction.dao.DomainDAO;
import com.hung.auction.dao.hibernate.HibernateDomainDAO;
import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@TransactionConfiguration
@Transactional
public class DomainServiceMockTest  {
    
    // http://www.easymock.org/EasyMock3_1_Documentation.html

    private static Logger log = Logger.getLogger(DomainServiceMockTest.class);
    
    @Autowired
    @Qualifier("domainServiceMock")
    private DomainService domainService;    // class under-test, this instance will be dependency injected by name
    
    private DomainDAO domainDAOMock;    // mock
    
    @Before
    public void setUp() {  
        this.domainDAOMock = EasyMock.createMock(HibernateDomainDAO.class);   // start mock
        this.domainService.setDomainDAO(this.domainDAOMock);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testQueryRoot() {
        log.info("////////// DomainServiceMockTest : testQueryRoot - start //////////");
        
        Domain rootDomain = new Domain(Domain.ROOT_NAME);
        
        EasyMock.expect(this.domainDAOMock.findByName(Domain.ROOT_NAME)).andReturn(rootDomain);
        EasyMock.replay(this.domainDAOMock);
        
        String expectedDomainName = rootDomain.getName();
        String actualDomainName = this.domainService.findByName(Domain.ROOT_NAME).getName();
        Assert.assertEquals(expectedDomainName, actualDomainName);
        
        EasyMock.verify(this.domainDAOMock);
        
        log.info("////////// DomainServiceMockTest : testQueryRoot - end //////////");
    }
    
    @Test(expected = RuntimeException.class)
    public void testDAOThrowsRuntimeException() throws RuntimeException {
        EasyMock.expect(this.domainDAOMock.findByName("testDAOThrowsRuntimeException")).andThrow(new RuntimeException("Fatal data access exception."));
        EasyMock.replay(this.domainDAOMock);
        String actualDomainName = this.domainService.findByName("testDAOThrowsRuntimeException").getName();
    }
}
