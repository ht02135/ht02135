package com.hung.service;

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

    private static Logger log = Logger.getLogger(DomainServiceMockTest.class);
    
    @Autowired
    @Qualifier("domainServiceMock")
    private DomainService domainService;    // class under-test, this instance will be dependency injected by name
    
    private DomainDAO domainDAOMock;    // mock
    
    @Before
    public void setUp() {  
        this.domainDAOMock = EasyMock.createStrictMock(HibernateDomainDAO.class);   // start mock
    }
    
    @After
    public void tearDown() {
        // execute "tear down" logic within the transaction
    }

    @Test
    public void testQueryRoot() {
        log.info("////////// DomainServiceMockTest : testQueryRoot - start //////////");
        
        Domain rootDomain = new Domain(Domain.ROOT_NAME);
        
        // add behavior for that method
        EasyMock.expect(this.domainDAOMock.findByName(Domain.ROOT_NAME)).andReturn(rootDomain);
        EasyMock.replay(this.domainDAOMock);
        // inject domainDAOMock into domainService in the container
        this.domainService.setDomainDAO(this.domainDAOMock);
        
        String expectedDomainName = rootDomain.getName();
        String actualDomainName = this.domainDAOMock.findByName(Domain.ROOT_NAME).getName();
        // test the return
        Assert.assertEquals(expectedDomainName, actualDomainName);
        
        log.info("////////// DomainServiceMockTest : testQueryRoot - end //////////");
    }
}
