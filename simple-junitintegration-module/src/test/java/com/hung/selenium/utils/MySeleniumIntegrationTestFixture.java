package com.hung.selenium.utils;

import org.junit.Rule;

import com.hung.selenium.utils.testwatcher.CaptureFailedTestWatcher;

public class MySeleniumIntegrationTestFixture {

    protected static MySelenium selenium = new MyDefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8081/simple-restfulwebapp-module/auction/");
    
    @Rule
    public static CaptureFailedTestWatcher testWatcher = new CaptureFailedTestWatcher(selenium, "chrome");
}
