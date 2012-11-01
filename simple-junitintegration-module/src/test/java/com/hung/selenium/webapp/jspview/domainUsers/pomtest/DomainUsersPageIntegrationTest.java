package com.hung.selenium.webapp.jspview.domainUsers.pomtest;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import com.hung.selenium.pom.domainUsers.DomainUsersPage;
import com.hung.selenium.utils.MyDefaultSelenium;
import com.hung.selenium.utils.MySelenium;

public class DomainUsersPageIntegrationTest {
    
    private static Logger log = Logger.getLogger(DomainUsersPageIntegrationTest.class);
    
    private MySelenium selenium;
    private DomainUsersPage domainUsersPage;
    
    @Before
    public void setUp() throws IOException{
        selenium = new MyDefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8081/simple-restfulwebapp-module/auction/");
        selenium.start();
        domainUsersPage = new DomainUsersPage(selenium);
        domainUsersPage.open();
    }
    
    @Test
    public void testAdmin() {
        Assert.assertTrue(domainUsersPage.isViewAdminURLPresent());
        Assert.assertTrue(domainUsersPage.isEditAdminURLPresent());
    }
    
    @Test
    public void testNewUserURL() {
        Assert.assertTrue(domainUsersPage.isNewUserURLPresent());
        
        domainUsersPage.clickNewUserLink();
        Assert.assertTrue(domainUsersPage.isNewUserFormPresent());
    }

    @After
    public void tearDown() {
        selenium.close();
        selenium.stop();
    }   
}
