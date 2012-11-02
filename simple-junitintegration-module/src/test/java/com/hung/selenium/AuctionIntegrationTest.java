package com.hung.selenium;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class AuctionIntegrationTest {
    
    private static Logger log = Logger.getLogger(AuctionIntegrationTest.class);
    
    private Selenium selenium;
    
    // here i want clean selenium for each @Test scenario
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://127.0.0.1:8081/simple-restfulwebapp-module/auction/");
		selenium.start();
	}

	// @Test
	public void testAuctionTestPlan() throws Exception {
	    log.info("#################### testAuctionTestPlan - start ####################");
	    
		selenium.open("http://127.0.0.1:8081/simple-restfulwebapp-module/auction/");
		Assert.assertEquals("Home", selenium.getTitle());
		selenium.click("link=Log In!");
		selenium.waitForPageToLoad("30000");
		Assert.assertEquals("Login", selenium.getTitle());
		selenium.type("id=domainName", "root");
		selenium.type("id=loginId", "admin");
		selenium.click("name=login");
		selenium.waitForPageToLoad("30000");
		Assert.assertEquals("logged In Home", selenium.getTitle());
		selenium.click("link=Domain Users");
		selenium.waitForPageToLoad("30000");
		Assert.assertEquals("List Domains", selenium.getTitle());
		selenium.click("link=Create a new domain user");
		selenium.waitForPageToLoad("30000");
		Assert.assertEquals("View Domain", selenium.getTitle());
		selenium.type("id=loginId", "test3");
		selenium.type("id=name", "test3");
		selenium.click("name=submit");
		selenium.waitForPageToLoad("30000");
		Assert.assertEquals("View Domain", selenium.getTitle());
		
		log.info("#################### testAuctionTestPlan - end ####################");
	}
	
	@Test
	public void testAuctionTestPlanXPath() throws Exception {
	    log.info("#################### testAuctionTestPlanXPath - start ####################");
	    
	    // ##############################################################
	    // ########## open : check basic acceptance condition #########
	    
	    selenium.open("http://127.0.0.1:8081/simple-restfulwebapp-module/auction/");
	    
	    // ########## open : check basic acceptance condition #########
	    
	    String jsessionid = selenium.getCookieByName("JSESSIONID");
	    // log.info("jsessionid="+jsessionid);
	    int matchingLoginURLCount = selenium.getXpathCount("//a[@href='/simple-restfulwebapp-module/auction/login;jsessionid="+jsessionid+"']").intValue();
	    Assert.assertTrue(matchingLoginURLCount > 0);
	    
	    // ##############################################################
	    // ########## click login URL #########
	    
	    //click on login url => <a href="/simple-restfulwebapp-module/auction/login;jsessionid=1l39ws9z5ajzi">Log In!</a>
	    // starts-with is XPath function
	    
	    selenium.click("//a[@href='/simple-restfulwebapp-module/auction/login;jsessionid="+jsessionid+"']");
	    
	    // wait 30 sec for the page to reload.  timeout with an exception and fail test, if over 30secs
	    selenium.waitForPageToLoad("30000");
	    
	    // ########## click login URL : check basic acceptance condition #########
	    
	    // check domainName and loginId field are present
        Assert.assertTrue(selenium.isElementPresent("//input[@id='domainName']"));
        Assert.assertTrue(selenium.isElementPresent("//input[@id='loginId']"));
	    
        // ##############################################################
	    // ########## enter login info #########
	    
	    // //*[@id="domainName"]   <-- XPath copied via FirePath
	    // <input type="text" size="15" value="" name="domainName" id="domainName">    <-- html
	    selenium.type("//input[@id='domainName']", "root");    // enter root for //input[@id='domainName']
	    
	    // //*[@id="loginId"]  <-- XPath copied via FirePath
	    // <input type="text" maxlength="15" size="15" value="" name="loginId" id="loginId">   <-- html
	    selenium.type("//input[@id='loginId']", "admin");    // enter admin for //input[@id='loginId']
	    
	    // /html/body/table/tbody/tr[2]/td[2]/div[2]/form/fieldset/table/tbody/tr[3]/td/input
	    // <input type="submit" value="Login" name="login">
	    selenium.click("//input[@name='login' and @type='submit']");
	    selenium.waitForPageToLoad("30000");
	    
	    // ########## enter login info : check bsic acceptance condition #########
	    
	    // look in html source
	    String htmlSource = selenium.getHtmlSource();
	    // log.info("htmlSource="+htmlSource);
	    Assert.assertTrue(htmlSource.contains("Welcome!!!!"));
	    
        // look in the page
        Assert.assertTrue(selenium.isTextPresent("Welcome!!!!"));
        
        // ##############################################################
        // ########## click Domain Users URL #########
        
        // /html/body/table/tbody/tr[2]/td/div/h2[3]/a
        // <a href="/simple-restfulwebapp-module/auction/domainUsers">Domain Users</a>
        selenium.click("//a[@href='/simple-restfulwebapp-module/auction/domainUsers']");
        selenium.waitForPageToLoad("30000");
        
        // ########## click Domain Users URL : check bsic acceptance condition #########
        
        // check if admin exists
        // /html/body/table/tbody/tr[2]/td[2]/div[2]/table/tbody/tr[5]/td[4]/a
        // <a href="/simple-restfulwebapp-module/auction/domainUsers/admin">View</a>
        Assert.assertTrue(selenium.isElementPresent("//a[@href='/simple-restfulwebapp-module/auction/domainUsers/admin']"));
	    
	    log.info("#################### testAuctionTestPlanXPath - end ####################");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
