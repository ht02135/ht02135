package com.hung.selenium.webapp.jspview.login.pomtest;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import com.hung.selenium.pom.login.LoginPage;
import com.hung.selenium.utils.MyDefaultSelenium;
import com.hung.selenium.utils.MySelenium;

public class LoginPageIntegrationTest {
    
    private static Logger log = Logger.getLogger(LoginPageIntegrationTest.class);
    
    private MySelenium selenium;
    private LoginPage loginPage;
    
    @Before
    public void setUp() throws IOException{
        selenium = new MyDefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8081/simple-restfulwebapp-module/auction/");
        selenium.start();
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
