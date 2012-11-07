package com.hung.selenium.pom;

import org.apache.log4j.Logger;

import com.hung.utils.selenium.MySelenium;

public class HomePage extends AbstractPage {
    
    private static Logger log = Logger.getLogger(HomePage.class);
    
    public static final String HOME_URL = "/simple-restfulwebapp-module/auction/";
    
    public HomePage(MySelenium selenium) {
        super(selenium);
    }
    
    public void open() {
        selenium.open(HOME_URL);
    }
}
