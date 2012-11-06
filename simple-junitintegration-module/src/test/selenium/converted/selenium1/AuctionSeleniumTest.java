package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class AuctionSeleniumTest {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://change-this-to-the-site-you-are-testing/");
		selenium.start();
	}

	@Test
	public void testAuctionTestPlan() throws Exception {
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
