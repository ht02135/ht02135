package com.hung.experiment.convertedfromrecorded.selenium2webdriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AuctionSeleniumIDETest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://change-this-to-the-site-you-are-testing/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testAuctionSeleniumIDE() throws Exception {
		driver.get("http://127.0.0.1:8081/simple-restfulwebapp-module/auction/");
		assertEquals("Home", driver.getTitle());
		driver.findElement(By.linkText("Log In!")).click();
		assertEquals("Login", driver.getTitle());
		driver.findElement(By.id("domainName")).clear();
		driver.findElement(By.id("domainName")).sendKeys("root");
		driver.findElement(By.id("loginId")).clear();
		driver.findElement(By.id("loginId")).sendKeys("admin");
		driver.findElement(By.name("login")).click();
		assertEquals("logged In Home", driver.getTitle());
		driver.findElement(By.linkText("Domain Users")).click();
		assertEquals("List Domains", driver.getTitle());
		driver.findElement(By.linkText("Create a new domain user")).click();
		assertEquals("View Domain", driver.getTitle());
		driver.findElement(By.id("loginId")).clear();
		driver.findElement(By.id("loginId")).sendKeys("test3");
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("test3");
		driver.findElement(By.name("submit")).click();
		assertEquals("View Domain", driver.getTitle());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
