package com.hung.selenium.utils;

import com.thoughtworks.selenium.Selenium;

public interface MySelenium extends Selenium {
    
    public int getCSSCount(String aCSSLocator);
}
