package com.hung.utils.selenium;

import com.thoughtworks.selenium.Selenium;

public interface MySelenium extends Selenium {
    
    public int getCSSCount(String aCSSLocator);
}
