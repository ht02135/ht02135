package com.hung.fixture;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hung.auction.dao.DomainDAO;
import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

/*
 test fixture is a fixed state of a set of objects used as a baseline for running tests. In other word, 
 creating a test fixture is to create a set of objects initialized to certain states.
 1>Loading a database with a specific, known set of data
 2>Copying a specific known set of files
 3>Preparation of input data and setup/creation of fake or mock objects
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
public class DomainMockFixture  {
    
    private static Logger log = Logger.getLogger(DomainMockFixture.class);
    
    protected DomainDAO domainDAOMock;
    
    protected Hashtable<String, Domain> domainNameDomainMappings = null;
    
    @Autowired
    @Qualifier("domainServiceWithDAOMock")
    protected DomainService domainServiceWithDAOMock;  
    
    // setUp() and tearDown()
    
    @Before
    public void setUp() {  
        domainDAOMock = EasyMock.createMock(DomainDAO.class);   // start mock
        domainNameDomainMappings = new Hashtable<String,Domain>();
        
        // no need to setup domainServiceWithDAOMock, cuz is auto-woired-in
        domainServiceWithDAOMock.setDomainDAO(domainDAOMock);
        
        // setup domainNameDomainMappings
        Domain root = new Domain("root", "root", null);
        Domain subRoot = new Domain("subroot", "subroot", root);
        domainNameDomainMappings.put(root.getName(), root);
        domainNameDomainMappings.put(subRoot.getName(), subRoot);
    }
    
    @After
    public void tearDown() {
    }
    
    // utility methods
}
