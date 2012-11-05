package com.hung.junit.domain;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hung.auction.domain.Domain;

// simple test to see if simple cobertura is configured correctly
public class DomainTest {

    private static Logger log = Logger.getLogger(DomainTest.class);
    
    @Before
    public void before() {
    }
    
    @After
    public void after() {
    }

    @Test
    public void testDummy() {
        log.info("////////// testDummy - start //////////");
        
        Domain domain = new Domain("testDummy", "testDummy");
        String actualName = domain.getName();
        String actualDescription = domain.getDescription();
        Assert.assertEquals("testDummy", actualName);
        Assert.assertEquals("testDummy", actualDescription);
        
        log.info("////////// testDummy - end //////////");
    }
}
