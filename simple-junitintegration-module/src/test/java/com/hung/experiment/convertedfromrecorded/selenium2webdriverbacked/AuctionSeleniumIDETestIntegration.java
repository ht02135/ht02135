package com.hung.experiment.convertedfromrecorded.selenium2webdriverbacked;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.SeleneseTestCase;

public class AuctionSeleniumIDETestIntegration extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://change-this-to-the-site-you-are-testing/";
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	@Test
	public void testAuctionSeleniumIDE() throws Exception {
		selenium.open("http://127.0.0.1:8081/simple-restfulwebapp-module/auction/");
		assertEquals("Home", selenium.getTitle());
		selenium.click("link=Log In!");
		selenium.waitForPageToLoad("30000");
		assertEquals("Login", selenium.getTitle());
		selenium.type("id=domainName", "root");
		selenium.type("id=loginId", "admin");
		selenium.click("name=login");
		selenium.waitForPageToLoad("30000");
		assertEquals("logged In Home", selenium.getTitle());
		selenium.click("link=Domain Users");
		selenium.waitForPageToLoad("30000");
		assertEquals("List Domains", selenium.getTitle());
		selenium.click("link=Create a new domain user");
		selenium.waitForPageToLoad("30000");
		assertEquals("View Domain", selenium.getTitle());
		selenium.type("id=loginId", "test3");
		selenium.type("id=name", "test3");
		selenium.click("name=submit");
		selenium.waitForPageToLoad("30000");
		assertEquals("View Domain", selenium.getTitle());
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
