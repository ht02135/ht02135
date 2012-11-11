package com.hung.jmeterintegration.selenium2webdriver.webapp.jspview.login;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.hung.selenium2webdriver.pom.login.LoginWDPage;

public class LoginWebDriverJMeterIntegration {
	
	private static Logger log = Logger.getLogger(LoginWebDriverJMeterIntegration.class);
	
	public static final String BASE_RUL = "http://localhost:8081";
	
	private WebDriver driver;
	private LoginWDPage loginWDPage;
	
    @Before
    public void setUp() throws IOException{
        driver = new FirefoxDriver(new FirefoxProfile());
    	loginWDPage = new LoginWDPage(driver, BASE_RUL);  
    }
    
    @Test
    public void testLogin() {
        log.info("########## testLogin : start ##########");
        
        loginWDPage.visit();
          
        // verify login form
        log.info("verify login form and fields");
        Assert.assertTrue(loginWDPage.getLoginForm() != null);
        Assert.assertTrue(loginWDPage.getDomainNameInput() != null);
        Assert.assertTrue(loginWDPage.getLoginIdInput() != null);
        Assert.assertTrue(loginWDPage.getSubmitInput() != null);

        // login
        loginWDPage.login("root", "admin");
        Assert.assertTrue(loginWDPage.contains("Welcome!!!!"));

        log.info("########## testLogin : end ##########");
    }
    
    @After
    public void tearDown() {
    	driver.quit();	// Quits this driver, closing every associated window.
    }  
}

