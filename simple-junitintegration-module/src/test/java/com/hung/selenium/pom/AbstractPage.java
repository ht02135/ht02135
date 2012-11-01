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
}
