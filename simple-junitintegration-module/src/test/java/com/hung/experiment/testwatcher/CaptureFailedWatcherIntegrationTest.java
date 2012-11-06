package com.hung.experiment.testwatcher;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MySeleniumIntegrationTestFixture;

public class CaptureFailedWatcherIntegrationTest extends MySeleniumIntegrationTestFixture {
    
    private static Logger log = Logger.getLogger(CaptureFailedWatcherIntegrationTest.class);
    
    private static LoginPage loginPage = new LoginPage(selenium);
    
    @BeforeClass
    public static void setUpOnce() throws IOException{
        selenium.start();
        loginPage.open();
    }
    
    /* successful, but want to comment out
    @Test
    public void testFailedLogin() {
        // enter wrong input
        loginPage.typeDomainName("testFailedLogin");
        loginPage.typeLoginId("testFailedLogin");
        loginPage.clickSubmit();
        
        Assert.assertTrue(loginPage.isTextPresent("Welcome!!!!"));
    }
    */
    
    @Test
    public void testLogin() {
        loginPage.selectDomainName("root");
        loginPage.typeLoginId("admin");
        loginPage.clickSubmit();
        
        Assert.assertTrue(loginPage.isTextPresent("Welcome!!!!"));
    }
    
    @AfterClass
    public static void tearDownOnce() {
        selenium.close();
        selenium.stop();
    }   
}
