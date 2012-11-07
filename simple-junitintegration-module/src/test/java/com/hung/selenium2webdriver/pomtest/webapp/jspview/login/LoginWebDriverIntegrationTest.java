package com.hung.selenium2webdriver.pomtest.webapp.jspview.login;

import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.hung.selenium2webdriver.pom.login.LoginWDPage;
import com.hung.utils.parameters.domainUsers.DomainUserParameters;
import com.hung.utils.selenium2webdriver.MyWebDriverIntegrationTestFixture;

@RunWith(Parameterized.class)
public class LoginWebDriverIntegrationTest extends MyWebDriverIntegrationTestFixture {
	
	private static Logger log = Logger.getLogger(LoginWebDriverIntegrationTest.class);

	private static LoginWDPage loginWDPage;
	
    private String domainName;
    private String loginId;
    
    public LoginWebDriverIntegrationTest(String domainName, String loginId) {
        this.domainName = domainName;
        this.loginId = loginId;
    }
    
    @Parameters
    public static Collection data() {
        return DomainUserParameters.DOMAIN_USER_LOGINIDS_FILE_DATA;
    }
    
    @BeforeClass
    public static void setUpOnce() throws IOException{
    	loginWDPage = new LoginWDPage(driver, BASE_RUL);  
    }
    
    @Test
    public void testLogin() {
        log.info("########## testLogin : start ##########");
        
        loginWDPage.visit();
        
        Assert.assertTrue(loginWDPage.getForm() != null);
        Assert.assertTrue(loginWDPage.getDomainNameInput() != null);
        Assert.assertTrue(loginWDPage.getLoginIdInput() != null);
        Assert.assertTrue(loginWDPage.getSubmitInput() != null);
        
        log.info("domainName="+domainName);
        log.info("loginId="+loginId);
        
        loginWDPage.login(domainName, loginId);
        Assert.assertTrue(loginWDPage.contains("Welcome!!!!"));
        
        log.info("########## testLogin : end ##########");
    }
    
    @AfterClass
    public static void tearDownOnce() {
    	driver.quit();
    }   
}
