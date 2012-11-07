package com.hung.selenium2webdriver.pom;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class HomeWDPage extends AbstractWDPage {
	
	private static Logger log = Logger.getLogger(HomeWDPage.class);
	
	public static final String HOME_URL = "/simple-restfulwebapp-module/auction/";

    public HomeWDPage(WebDriver driver, String baseURL) {
        super(driver, baseURL);
    }
    
    public void visit() {
    	// driver.navigate().to(baseURL + HOME_URL);
    	driver.get(baseURL + HOME_URL);
    }
}
