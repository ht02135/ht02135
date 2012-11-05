package com.hung.selenium2webdriverbacked.webapp.jspview.login.pomtest;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.hung.fixture.MyWebDriverBackedSeleniumIntegrationTestFixture;
import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MySelenium;
import com.hung.utils.selenium2webdriverbacked.MyWebDriverBackedSelenium;

public class LoginPageWebDrivenBackedIntegrationTest extends MyWebDriverBackedSeleniumIntegrationTestFixture {
    
    private static Logger log = Logger.getLogger(LoginPageWebDrivenBackedIntegrationTest.class);
    
    private static MySelenium selenium;
    private static LoginPage loginPage;
    
    // here i want clean selenium for each @Test scenario
    @BeforeClass
    public static void setUpOnce() throws IOException{
        FirefoxProfile profile = new FirefoxProfile();
        WebDriver driver = new FirefoxDriver(profile);
        selenium = new MyWebDriverBackedSelenium(driver, "http://localhost:8081");
        
        loginPage = new LoginPage(selenium);
        loginPage.open();
    }
    
    @Test
    public void testLogin() {
        Assert.assertTrue(loginPage.isDomainNameFieldPresent());
        Assert.assertTrue(loginPage.isLoginIdFieldPresent());
        Assert.assertTrue(loginPage.isSubmitButtonPresent());
        
        loginPage.typeDomainName("root");
        loginPage.typeLoginId("admin");
        loginPage.clickSubmit();
        
        Assert.assertTrue(loginPage.isTextPresent("Welcome!!!!"));
        // to capture failed screen, force a failure
        // Assert.assertTrue(loginPage.isTextPresent("Force failure"));
    }

    @AfterClass
    public static void tearDownOnce() {
        selenium.close();
        selenium.stop();
    }   
}
