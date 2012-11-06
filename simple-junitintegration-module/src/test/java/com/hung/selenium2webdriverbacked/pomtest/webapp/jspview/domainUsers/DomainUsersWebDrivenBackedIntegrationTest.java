package com.hung.selenium2webdriverbacked.pomtest.webapp.jspview.domainUsers;

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

import com.hung.selenium.pom.domainUsers.DomainUsersPage;
import com.hung.utils.parameters.domainUsers.DomainUserParameters;
import com.hung.utils.selenium2webdriverbacked.MyWebDriverBackedSeleniumIntegrationTestFixture;

@RunWith(Parameterized.class)
public class DomainUsersWebDrivenBackedIntegrationTest extends MyWebDriverBackedSeleniumIntegrationTestFixture {

    private static Logger log = Logger.getLogger(DomainUsersWebDrivenBackedIntegrationTest.class);
    
    private static DomainUsersPage domainUsersPage;
    
    private String loginId;
    private String name;
    private String domainName;
    
    public DomainUsersWebDrivenBackedIntegrationTest(String loginId, String name, String domainName) {
        this.loginId = loginId;
        this.name = name;
        this.domainName = domainName;
    }
    
    @Parameters
    public static Collection data() {
    	// webdriverbacked_domain_user_param4,webdriverbacked_domain_user_param4,subroot
        return DomainUserParameters.NEW_DOMAIN_USERS_WEBDRIVERBACKED_FILE_DATA;
    }
    
    // here i want clean selenium for each @Test scenario
    @BeforeClass
    public static void setUpOnce() throws IOException{
    	domainUsersPage = new DomainUsersPage(selenium);
    }
    
    @Test
    public void testCreateNewUser() {
        log.info("########## testCreateNewUser : start ##########");
        
        domainUsersPage.open();
        log.info("domainUsersPage.isNewUserLinkPresent()="+domainUsersPage.isNewUserLinkPresent());
        Assert.assertTrue(domainUsersPage.isNewUserLinkPresent());
        
        domainUsersPage.clickNewUserLink();
        log.info("domainUsersPage.isNewUserFormPresent()="+domainUsersPage.isNewUserFormPresent());
        Assert.assertTrue(domainUsersPage.isNewUserFormPresent());
        
        Assert.assertTrue(domainUsersPage.isLoginIdFieldPresent());
        Assert.assertTrue(domainUsersPage.isNameFieldPresent());
        Assert.assertTrue(domainUsersPage.isDomainNameFieldPresent());
        Assert.assertTrue(domainUsersPage.isSubmitButtonPresent());
        
        log.info("loginId="+loginId);
        log.info("name="+name);
        log.info("domainName="+domainName);
        
        domainUsersPage.typeLoginId(loginId);
        domainUsersPage.typeName(name);
        domainUsersPage.selectDomainName(domainName);
        domainUsersPage.clickSubmit();
        
        log.info("domainUsersPage.isTextPresent(loginId)="+domainUsersPage.isTextPresent(loginId));
        Assert.assertTrue(domainUsersPage.isTextPresent(loginId));
        
        log.info("########## testCreateNewUser : end ##########");
    }
    
    @AfterClass
    public static void tearDownOnce() {
        selenium.close();
        selenium.stop();
    } 
}
