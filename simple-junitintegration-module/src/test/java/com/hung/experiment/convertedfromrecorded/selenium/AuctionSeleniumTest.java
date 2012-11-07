package com.hung.experiment.convertedfromrecorded.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class AuctionSeleniumTest {
	
	private Selenium selenium;
	
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://change-this-to-the-site-you-are-testing/");
		selenium.start();
	}

	@Test
	public void testAuctionTestPlan() throws Exception {
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
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
