package com.hung.utils.testwatcher;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.hung.utils.common.SettingConstants;
import com.hung.utils.selenium.MySelenium;

public class DefaultSeleniumCaptureFailedTestWatcher extends TestWatcher {
    
    private static Logger log = Logger.getLogger(DefaultSeleniumCaptureFailedTestWatcher.class);
    
    protected MySelenium selenium;
    protected String browser;
    protected String screenShotFolder;
    
    public DefaultSeleniumCaptureFailedTestWatcher(MySelenium selenium, String browser) {
        super();
        this.selenium = selenium;
        this.browser = browser;
        this.screenShotFolder = createNowfolder();
    }
    
    // ################### override ###################

    @Override
    public void failed(Throwable e, Description description) {
        log.info("########## failed : start ##########");
        captureScreenShot(description.getMethodName() + "_" + description.getClassName());
        log.info("########## failed : end ##########");
    }
    
    // ################### private ###################
    
    protected void captureScreenShot(String fileNameAppend) {
        log.info("########## captureScreenShot : start ##########");
        log.info("fileNameAppend="+fileNameAppend);
        
        try { 
            selenium.windowMaximize();
            
            Thread.sleep(2000); // give the browser a chance to maximise properly
            
            String filename = screenShotFolder + File.separator + browser + "_" + fileNameAppend + "_screenshot.png";
            selenium.captureScreenshot(filename);
            selenium.captureEntirePageScreenshot(filename.replace(".png","full.png"), "");   
        } catch (Exception e) {
            log.error("e="+e);
        }
        
        log.info("########## captureScreenShot : end ##########");
    }
    
    private String createNowfolder() {
        String screenShotFolderName = getScreenShotFolderFromProp("src/test/resources/settings/settings.properties") + File.separator;
        log.info("createNowfolder : screenShotFolderName="+screenShotFolderName);
        
        SimpleDateFormat sdfmth = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        screenShotFolderName = screenShotFolderName + sdfmth.format(cal.getTime());
        log.info("createNowfolder : screenShotFolderName="+screenShotFolderName);
        
        new File(screenShotFolderName).mkdirs();
        
        return screenShotFolderName;
    }
    
    private String getScreenShotFolderFromProp(String propertiesFile) {
        log.info("getScreenShotFolderFromProp : propertiesFile="+propertiesFile);
        
        String screenShotFolderName = "";
        try {
            File file = new File(propertiesFile);
            InputStream stream = FileUtils.openInputStream(file);
            Properties properties = new Properties();
            properties.load(stream);
            screenShotFolderName = properties.getProperty(SettingConstants.SCREEN_SHOT_FOLDER);
            log.info("getScreenShotFolderFromProp : screenShotFolderName="+screenShotFolderName);
        } catch (IOException e) {
            log.error("e="+e);
        }
        
        return screenShotFolderName;
    }

}
