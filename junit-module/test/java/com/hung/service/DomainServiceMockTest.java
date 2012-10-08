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
    
    // http://www.easymock.org/EasyMock3_1_Documentation.html

    private static Logger log = Logger.getLogger(DomainServiceMockTest.class);
    
    @Autowired
    @Qualifier("domainServiceMock")
    private DomainService domainService;    // class under-test, this instance will be dependency injected by name
    
    private DomainDAO domainDAOMock;    // mock
    
    @Before
    public void setUp() {  
        // usually the place to create service and dao-mock.  but my service is dependency-inject
        // so only need to create mock
        
        /*
        1>order of method calls:
          On a Mock Object returned by a EasyMock.createMock(), the order of method calls is not checked. If you would 
          like a strict Mock Object that checks the order of method calls, use EasyMock.createStrictMock() to create it.
        2>nice mock:
          On a Mock Object returned by createMock() the default behavior for all methods is to throw an AssertionError 
          for all unexpected method calls. If you would like a "nice" Mock Object that by default allows all method calls 
          and returns appropriate empty values (0, null or false), use createNiceMock() instead. 
        
        3>Creating a mock is simple. Simply call createMock and pass in the class of the interface to be mocked
        */
        this.domainDAOMock = EasyMock.createStrictMock(HibernateDomainDAO.class);   // start mock
        // inject domainDAOMock into domainService in the container
        this.domainService.setDomainDAO(this.domainDAOMock);
    }
    
    @After
    public void tearDown() {
        // execute "tear down" logic within the transaction
    }

    @Test
    public void testQueryRoot() {
        log.info("////////// DomainServiceMockTest : testQueryRoot - start //////////");
        
        Domain rootDomain = new Domain(Domain.ROOT_NAME);
        
        // After creating our mock object, we next need to tell EasyMock how we expect that mock to be used, 
        // that is, what methods we expect to be called on it.  
        // add behavior for that method or rather Set expectations on mocks
        EasyMock.expect(this.domainDAOMock.findByName(Domain.ROOT_NAME)).andReturn(rootDomain);
        
        // Calling replay tells EasyMock that you are no longer specifying expected method calls and moving into replay mode.
        // Set mocks into testing mode.
        EasyMock.replay(this.domainDAOMock);
        
        String expectedDomainName = rootDomain.getName();
        // Run the method.
        String actualDomainName = this.domainService.findByName(Domain.ROOT_NAME).getName();
        // test the return
        Assert.assertEquals(expectedDomainName, actualDomainName);
        
        // The final step is where we ask EasyMock to confirm that all our expectations were met. That is, we ask EasyMock 
        // to confirm that a method call corresponding to each expectation we set is actually made.
        // Verify behavior.
        EasyMock.verify(this.domainDAOMock);
        
        log.info("////////// DomainServiceMockTest : testQueryRoot - end //////////");
    }
    
    @Test(expected = RuntimeException.class)
    public void testDAOThrowsRuntimeException() throws RuntimeException {
        // Set expectations on mocks.
        EasyMock.expect(this.domainDAOMock.findByName("testDAOThrowsRuntimeException")).andThrow(new RuntimeException("Fatal data access exception."));

        // Set mocks into testing mode.
        EasyMock.replay(this.domainDAOMock);

        // Run the method.
        String actualDomainName = this.domainService.findByName("testDAOThrowsRuntimeException").getName();
    }
}
