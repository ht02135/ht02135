package com.hung.utils.selenium2webdriver;

import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.hung.utils.testwatcher.WebDriverSeleniumCaptureFailedTestWatcher;

public class MyWebDriverIntegrationTestFixture {
	
	public static final String BASE_RUL = "http://localhost:8081";	// timeInSeconds
	
    protected static WebDriver driver;
    static
    {
        FirefoxProfile profile = new FirefoxProfile();
        driver = new FirefoxDriver(profile);
    }
    
    @Rule
    public static WebDriverSeleniumCaptureFailedTestWatcher testWatcher = new WebDriverSeleniumCaptureFailedTestWatcher(driver, "firefox");
}
