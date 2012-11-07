package com.hung.selenium2webdriver.pom;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractWDPage {
	
	private static Logger log = Logger.getLogger(AbstractWDPage.class);
	
	public static final long WAIT_TIME_IN_SECONDS = 5;	// timeInSeconds
	
	protected WebDriver driver;
	protected String baseURL;
	
    public AbstractWDPage(WebDriver driver, String baseURL) {
        this.driver = driver;
        this.baseURL = baseURL;
    }
    
    public WebElement explicitWaitUntilWebElementIsVisible(String ccsSelector, long waitTimeInSeconds) {
    	WebDriverWait wait = new WebDriverWait(driver, waitTimeInSeconds);
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ccsSelector)));
    }
    
    public Timeouts implicitWait(long waitTimeInSeconds) {
    	return driver.manage().timeouts().implicitlyWait(waitTimeInSeconds, TimeUnit.SECONDS);
    }
    
    public boolean contains(String text) {
    	return driver.getPageSource().contains(text);
    }
}
