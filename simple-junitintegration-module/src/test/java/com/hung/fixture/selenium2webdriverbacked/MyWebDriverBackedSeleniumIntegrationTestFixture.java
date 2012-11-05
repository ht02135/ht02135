package com.hung.fixture.selenium2webdriverbacked;

import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.hung.utils.selenium2webdriverbacked.MyWebDriverBackedSelenium;
import com.hung.utils.testwatcher.WebDriverSeleniumCaptureFailedTestWatcher;

public class MyWebDriverBackedSeleniumIntegrationTestFixture {
    
    protected static MyWebDriverBackedSelenium selenium;
    static
    {
        FirefoxProfile profile = new FirefoxProfile();
        WebDriver driver = new FirefoxDriver(profile);
        selenium = new MyWebDriverBackedSelenium(driver, "http://localhost:8081");
    }
    
    @Rule
    public static WebDriverSeleniumCaptureFailedTestWatcher testWatcher = new WebDriverSeleniumCaptureFailedTestWatcher(selenium, "firefox");
}
