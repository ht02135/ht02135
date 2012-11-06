package com.hung.selenium.pomtest.webapp.jspview.domainUsers;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hung.selenium.pom.domainUsers.DomainUsersPage;
import com.hung.utils.selenium.MyDefaultSelenium;
import com.hung.utils.selenium.MySelenium;

public class DomainUsersPageIntegrationTest {
    
    private static Logger log = Logger.getLogger(DomainUsersPageIntegrationTest.class);
    
    private MySelenium selenium;
    private DomainUsersPage domainUsersPage;
    
    // here i want clean selenium for each @Test scenario
    @Before
    public void setUp() throws IOException{
        selenium = new MyDefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8081/simple-restfulwebapp-module/auction/");
        selenium.start();
        domainUsersPage = new DomainUsersPage(selenium);
        domainUsersPage.open();
    }
    
    @Test
    public void testAdmin() {
        Assert.assertTrue(domainUsersPage.isAdminViewLinkPresent());
        Assert.assertTrue(domainUsersPage.isAdminEditLinkPresent());
    }
    
    @Test
    public void testNewUserURL() {
        Assert.assertTrue(domainUsersPage.isNewUserLinkPresent());
        
        domainUsersPage.clickNewUserLink();
        Assert.assertTrue(domainUsersPage.isNewUserFormPresent());
    }

    @After
    public void tearDown() {
        selenium.close();
        selenium.stop();
    }   
}
