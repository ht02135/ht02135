package com.hung.utils.testwatcher;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.hung.utils.selenium2webdriverbacked.MyWebDriverBackedSelenium;

public class WebDriverSeleniumCaptureFailedTestWatcher extends DefaultSeleniumCaptureFailedTestWatcher {

    private static Logger log = Logger.getLogger(WebDriverSeleniumCaptureFailedTestWatcher.class);
    
    protected WebDriver driver;
    
    public WebDriverSeleniumCaptureFailedTestWatcher(MyWebDriverBackedSelenium selenium, String browser) {
        super(selenium, browser);
        driver = selenium.getWrappedDriver();
    }
    
    public WebDriverSeleniumCaptureFailedTestWatcher(WebDriver driver, String browser) {
        super(null, browser);
        this.driver = driver;
    }
    
    protected void captureScreenShot(String fileNameAppend) {
        log.info("########## captureScreenShot : start ##########");
        log.info("fileNameAppend="+fileNameAppend);
        
        try { 
            selenium.windowMaximize();
            
            Thread.sleep(2000); // give the browser a chance to maximise properly
            
            File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String filename = screenShotFolder + File.separator + browser + "_" + fileNameAppend + "_screenshot.png";
            FileUtils.copyFile(screenshotFile, new File(filename));
        } catch (Exception e) {
            log.error("e="+e);
        }
        
        log.info("########## captureScreenShot : end ##########");
    }
}
