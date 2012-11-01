package com.hung.selenium.pom;

import org.apache.log4j.Logger;

import com.hung.selenium.utils.MySelenium;

public class HomePage extends AbstractPage {
    
    private static Logger log = Logger.getLogger(HomePage.class);
    
    public HomePage(MySelenium selenium) {
        super(selenium);
    }
    
    public void open() {
        selenium.open("/simple-restfulwebapp-module/auction/");
    }
}
