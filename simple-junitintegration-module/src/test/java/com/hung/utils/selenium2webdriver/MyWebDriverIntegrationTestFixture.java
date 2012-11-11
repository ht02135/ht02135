package com.hung.utils.selenium2webdriver;

import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.hung.utils.testwatcher.WebDriverSeleniumCaptureFailedTestWatcher;

public class MyWebDriverIntegrationTestFixture {
	
	public static final String BASE_RUL = "http://localhost:8081";
	
    protected static WebDriver driver;
    static
    {
        driver = new FirefoxDriver(new FirefoxProfile());
    }
    
    @Rule
    public static WebDriverSeleniumCaptureFailedTestWatcher testWatcher = new WebDriverSeleniumCaptureFailedTestWatcher(driver, "firefox");
}
