package com.hung.selenium.css;

import com.thoughtworks.selenium.Selenium;

public interface MySelenium extends Selenium {
    
    public int getCSSCount(String aCSSLocator);
}
