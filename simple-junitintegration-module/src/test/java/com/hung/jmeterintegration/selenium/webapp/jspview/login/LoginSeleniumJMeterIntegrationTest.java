package com.hung.jmeterintegration.selenium.webapp.jspview.login;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hung.jmeterintegration.selenium2webdriver.webapp.jspview.login.LoginWebDriverJMeterIntegration;
import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MyDefaultSelenium;
import com.hung.utils.selenium.MySelenium;

public class LoginSeleniumJMeterIntegrationTest {
	private static Logger log = Logger.getLogger(LoginWebDriverJMeterIntegration.class);
	
	public static final String BASE_RUL = "http://localhost:8081";
	
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
        log.info("########## testLogin : start ##########");
        
        // verify login form
        log.info("verify login form and fields");
        Assert.assertTrue(loginPage.isDomainNameFieldPresent());
        Assert.assertTrue(loginPage.isLoginIdFieldPresent());
        Assert.assertTrue(loginPage.isSubmitButtonPresent());

        // login
        loginPage.selectDomainName("root");
        loginPage.typeLoginId("admin");
        loginPage.clickSubmit();

        Assert.assertTrue(loginPage.isTextPresent("Welcome!!!!"));
            
        log.info("########## testLogin : end ##########");
    }
    
    @After
    public void tearDown() {
        selenium.close();
        selenium.stop();
    }  
}
