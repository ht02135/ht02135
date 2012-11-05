package com.hung.junit.integration.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.domain.Domain;
import com.hung.junit.fixture.DomainFixture;

/*
 test fixture is a fixed state of a set of objects used as a baseline for running tests. In other word, 
 creating a test fixture is to create a set of objects initialized to certain states.
 1>Loading a database with a specific, known set of data
 2>Copying a specific known set of files
 3>Preparation of input data and setup/creation of fake or mock objects
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@TransactionConfiguration
@Transactional
public class DomainTest extends DomainFixture  {
    
    // this test example just show how localizing fixture to other class help to reduce clutter
    @Test
    public void testFindDomainViaDAO() {
        Domain root = this.domainDAO.findByName("root");
        String expectedDomainName = "root";
        String actualDomainName = root.getName();
        Assert.assertEquals(expectedDomainName, actualDomainName);
    }
    
    @Test
    public void testFindDomainViaService() {
        Domain root = this.domainService.findByName("root");
        String expectedDomainName = "root";
        String actualDomainName = root.getName();
        Assert.assertEquals(expectedDomainName, actualDomainName);
    }
}
