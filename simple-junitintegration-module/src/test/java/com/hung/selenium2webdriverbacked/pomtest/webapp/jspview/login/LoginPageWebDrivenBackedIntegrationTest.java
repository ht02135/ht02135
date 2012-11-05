package com.hung.selenium2webdriverbacked.pomtest.webapp.jspview.login;

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

import com.hung.fixture.selenium2webdriverbacked.MyWebDriverBackedSeleniumIntegrationTestFixture;
import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.parameters.domainUsers.DomainUserFileParameters;

@RunWith(Parameterized.class)
public class LoginPageWebDrivenBackedIntegrationTest extends MyWebDriverBackedSeleniumIntegrationTestFixture {
    
    private static Logger log = Logger.getLogger(LoginPageWebDrivenBackedIntegrationTest.class);
    
    private static LoginPage loginPage;
    
    private String domainName;
    private String loginId;
    
    public LoginPageWebDrivenBackedIntegrationTest(String domainName, String loginId) {
        this.domainName = domainName;
        this.loginId = loginId;
    }
    
    @Parameters
    public static Collection data() {
        // JaxbDomainUser(String loginId, String name, String userDomainName)
        return new DomainUserFileParameters("src/test/resources/data/webdriverbacked/domainUserLoginIds.properties").data();
    }
    
    // here i want clean selenium for each @Test scenario
    @BeforeClass
    public static void setUpOnce() throws IOException{
        loginPage = new LoginPage(selenium);
        
    }
    
    @Test
    public void testLogin() {
        log.info("########## testLogin : start ##########");
        
        log.info("domainName="+domainName);
        log.info("loginId="+loginId);
        
        loginPage.open();
        
        Assert.assertTrue(loginPage.isDomainNameFieldPresent());
        Assert.assertTrue(loginPage.isLoginIdFieldPresent());
        Assert.assertTrue(loginPage.isSubmitButtonPresent());
        
        loginPage.typeDomainName(domainName);
        loginPage.typeLoginId(loginId);
        loginPage.clickSubmit();
        
        Assert.assertTrue(loginPage.isTextPresent("Welcome!!!!"));
        // to capture failed screen, force a failure
        // Assert.assertTrue(loginPage.isTextPresent("Force failure"));
        
        log.info("########## testLogin : end ##########");
    }

    @AfterClass
    public static void tearDownOnce() {
        selenium.close();
        selenium.stop();
    }   
}
