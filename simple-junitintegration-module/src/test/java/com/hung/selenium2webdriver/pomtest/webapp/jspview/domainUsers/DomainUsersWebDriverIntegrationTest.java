package com.hung.selenium2webdriver.pomtest.webapp.jspview.domainUsers;

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

import com.hung.selenium2webdriver.pom.domainUsers.DomainUsersWDPage;
import com.hung.utils.parameters.domainUsers.DomainUserParameters;
import com.hung.utils.selenium2webdriver.MyWebDriverIntegrationTestFixture;

@RunWith(Parameterized.class)
public class DomainUsersWebDriverIntegrationTest extends MyWebDriverIntegrationTestFixture {
	
    private static Logger log = Logger.getLogger(DomainUsersWebDriverIntegrationTest.class);
    
    private static DomainUsersWDPage domainUsersWDPage;
    
    private String loginId;
    private String name;
    private String domainName;
    
    public DomainUsersWebDriverIntegrationTest(String loginId, String name, String domainName) {
        this.loginId = loginId;
        this.name = name;
        this.domainName = domainName;
    }
    
    @Parameters
    public static Collection data() {
        return DomainUserParameters.NEW_DOMAIN_USERS_WEBDRIVER_FILE_DATA;
    }
    
    @BeforeClass
    public static void setUpOnce() throws IOException{
    	domainUsersWDPage = new DomainUsersWDPage(driver, BASE_RUL);  
    }
    
    @Test
    public void testCreateNewUser() {
        log.info("########## testCreateNewUser : start ##########");
        
        domainUsersWDPage.visit();	// go to domain user page
        
        // verify new domain user link
        Assert.assertTrue(domainUsersWDPage.getNewDomainUserLink() != null);
        
        // click new domain user link
        domainUsersWDPage.clickNewDomainUserLink();
        
        // verify new domain user form
        log.info("verify create domain user form and fields");
        Assert.assertTrue(domainUsersWDPage.getCreateDomainUserForm() != null);
        Assert.assertTrue(domainUsersWDPage.getLoginIdInput() != null);
        Assert.assertTrue(domainUsersWDPage.getNameInput() != null);
        Assert.assertTrue(domainUsersWDPage.getDomainNameSelect() != null);
        Assert.assertTrue(domainUsersWDPage.getSubmitInput() != null);
        
        domainUsersWDPage.createUser(loginId, name, domainName);
        Assert.assertTrue(domainUsersWDPage.contains(loginId));
        
        log.info("########## testCreateNewUser : end ##########");
    }
    
    @AfterClass
    public static void tearDownOnce() {
    	driver.quit();
    }  
}
