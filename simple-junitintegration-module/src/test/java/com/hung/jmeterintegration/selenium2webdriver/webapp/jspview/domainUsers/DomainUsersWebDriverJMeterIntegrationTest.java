package com.hung.jmeterintegration.selenium2webdriver.webapp.jspview.domainUsers;

import java.io.IOException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.hung.selenium2webdriver.pom.domainUsers.DomainUsersWDPage;

public class DomainUsersWebDriverJMeterIntegrationTest {
	
	private static Logger log = Logger.getLogger(DomainUsersWebDriverJMeterIntegrationTest.class);
	
	public static final String BASE_RUL = "http://localhost:8081";
	public static final String LOGIN_ID = "webdriver_jmeter_user";
	public static final String DOMAIN_NAME = "subroot";
	
	private static final Random RANDOM = new Random();
	
	private WebDriver driver;
	private DomainUsersWDPage domainUsersWDPage;
	
    @Before
    public void setUp() throws IOException{
    	driver = new FirefoxDriver(new FirefoxProfile());
    	domainUsersWDPage = new DomainUsersWDPage(driver, BASE_RUL);  
    }
    
    @Test
    public void testCreateNewUser() {
        log.info("########## testCreateNewUser : start ##########");
        
    	domainUsersWDPage.visit();	// go to domain user page
    	Assert.assertTrue(domainUsersWDPage.getNewDomainUserLink() != null);	// verify new domain user link
    	domainUsersWDPage.clickNewDomainUserLink();	// click new domain user link
        
        // verify new domain user form
        log.info("verify create domain user form and fields");
        Assert.assertTrue(domainUsersWDPage.getCreateDomainUserForm() != null);
        Assert.assertTrue(domainUsersWDPage.getLoginIdInput() != null);
        Assert.assertTrue(domainUsersWDPage.getNameInput() != null);
        Assert.assertTrue(domainUsersWDPage.getDomainNameSelect() != null);
        Assert.assertTrue(domainUsersWDPage.getSubmitInput() != null);

        int rn = Math.abs(RANDOM.nextInt());
        String loginId = LOGIN_ID+"_"+rn;
        String name = loginId;
        String domainName = DOMAIN_NAME;

        domainUsersWDPage.createUser(loginId, name, domainName);
        Assert.assertTrue(domainUsersWDPage.contains(loginId));
 
        log.info("########## testCreateNewUser : end ##########");
    }
    
    @After
    public void tearDownOnce() {
    	driver.quit();	// Quits this driver, closing every associated window.
    }  
}
