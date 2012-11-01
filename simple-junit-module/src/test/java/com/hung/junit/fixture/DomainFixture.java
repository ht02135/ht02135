package com.hung.junit.fixture;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hung.auction.dao.DomainDAO;
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
public class DomainFixture  {
    
    private static Logger log = Logger.getLogger(DomainFixture.class);
    
    @Autowired
    @Qualifier("domainDAO")
    protected DomainDAO domainDAO;
    
    @Autowired
    @Qualifier("domainService")
    protected DomainService domainService; 
    
    // setUp() and tearDown()
    
    @Before
    public void setUp() {  
        // no need to setup domainDAO, cuz is auto-wired-in
        // no need to setup domainService, cuz is auto-wired-in
    }
    
    @After
    public void tearDown() {
    }
    
    // utility methods
}
