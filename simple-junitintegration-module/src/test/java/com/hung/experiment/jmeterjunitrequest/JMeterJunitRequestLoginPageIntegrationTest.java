package com.hung.experiment.jmeterjunitrequest;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MyDefaultSelenium;
import com.hung.utils.selenium.MySelenium;

// created this class to see if can be picked up by JMeter UI : JUnit Request Sampler

public class JMeterJunitRequestLoginPageIntegrationTest extends TestCase {
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
        loginPage.selectDomainName("root");
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
