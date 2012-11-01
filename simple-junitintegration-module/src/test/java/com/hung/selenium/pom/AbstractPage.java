package com.hung.selenium.pom;

import com.hung.selenium.utils.MySelenium;

public abstract class AbstractPage {

    protected MySelenium selenium;
    
    public AbstractPage(MySelenium selenium) {
        this.selenium = selenium;
    }
    
    public boolean isTextPresent(String text) {
        return selenium.isTextPresent(text);
    }
    
    public boolean isAlertPresent() {
        return selenium.isAlertPresent();
    }
    
    public String getAlert() {
        return selenium.getAlert();
    }
    
    public void waitForPageToLoad(String time) {
        selenium.waitForPageToLoad(time);
    }
}
