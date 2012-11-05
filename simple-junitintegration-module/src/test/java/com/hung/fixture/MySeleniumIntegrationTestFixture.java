package com.hung.fixture;

import org.junit.Rule;

import com.hung.utils.selenium.MyDefaultSelenium;
import com.hung.utils.selenium.MySelenium;
import com.hung.utils.testwatcher.DefaultSeleniumCaptureFailedTestWatcher;

public class MySeleniumIntegrationTestFixture {

    protected static MySelenium selenium = new MyDefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8081/simple-restfulwebapp-module/auction/");
    
    @Rule
    public static DefaultSeleniumCaptureFailedTestWatcher testWatcher = new DefaultSeleniumCaptureFailedTestWatcher(selenium, "chrome");
}
