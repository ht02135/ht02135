package com.hung.selenium2webdriverbacked.webapp.jspview.login.pomtest;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MySelenium;
import com.hung.utils.selenium2webdriverbacked.MyWebDriverBackedSelenium;

public class LoginPageWebDrivenBackedIntegrationTest {
    
    private static Logger log = Logger.getLogger(LoginPageWebDrivenBackedIntegrationTest.class);
    
    private MySelenium selenium;
    private LoginPage loginPage;
    
    // here i want clean selenium for each @Test scenario
    @Before
    public void setUp() throws IOException{
        FirefoxProfile profile = new FirefoxProfile();
        WebDriver driver = new FirefoxDriver(profile);
        selenium = new MyWebDriverBackedSelenium(driver, "http://localhost:8081");
        
        loginPage = new LoginPage(selenium);
        loginPage.open();
    }
    
    @Test
    public void testLogin() {
        loginPage.typeDomainName("root");
        loginPage.typeLoginId("admin");
        loginPage.clickSubmit();
        
        Assert.assertTrue(loginPage.isTextPresent("Welcome!!!!"));
    }
    
    @Test
    public void testLoginFormFields() {
        Assert.assertTrue(loginPage.isDomainNameFieldPresent());
        Assert.assertTrue(loginPage.isLoginIdFieldPresent());
        Assert.assertTrue(loginPage.isSubmitButtonPresent());
    }

    @After
    public void tearDown() {
        selenium.close();
        selenium.stop();
    }   
}
