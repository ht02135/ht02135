package com.hung.selenium.pom.login;

import org.apache.log4j.Logger;

import com.hung.selenium.pom.AbstractPage;
import com.hung.selenium.pom.HomePage;
import com.hung.utils.selenium.MySelenium;

public class LoginPage extends AbstractPage {
    
    private static Logger log = Logger.getLogger(LoginPage.class);
    
    public static final String LOGIN_LINK_LOCATOR = "css=a[href^='/simple-restfulwebapp-module/auction/login']";
    
    public static final String LOGIN_FORM_DOMAIN_NAME_LOCATOR = "css=form#jaxbClientSession input#domainName";
    public static final String LOGIN_FORM_LOGIN_ID_LOCATOR = "css=form#jaxbClientSession input#loginId";
    public static final String LOGIN_FORM_SUBMIT_LOCATOR = "css=form#jaxbClientSession input[name='login'][type='submit']";
    
    public LoginPage(MySelenium selenium) {
        super(selenium);
    }
    
    public void open() {
    	HomePage homePage = new HomePage(selenium);
    	homePage.open();	// first visit home page
        
        /*
        both works, but i like the partial match
        String jsessionid = selenium.getCookieByName("JSESSIONID");
        selenium.click("css=a[href='/simple-restfulwebapp-module/auction/login;jsessionid="+jsessionid+"']");
        */
        log.info("selenium.isElementPresent(LOGIN_LINK_LOCATOR)="+selenium.isElementPresent(LOGIN_LINK_LOCATOR));
        selenium.click(LOGIN_LINK_LOCATOR);
        selenium.waitForPageToLoad("30000");
    }
    
    // #################### Is Login form fields present ####################
    
    public boolean isDomainNameFieldPresent() {
        return selenium.isElementPresent(LOGIN_FORM_DOMAIN_NAME_LOCATOR);
    }
    
    public boolean isLoginIdFieldPresent() {
        return selenium.isElementPresent(LOGIN_FORM_LOGIN_ID_LOCATOR);
    }
    
    public boolean isSubmitButtonPresent() {
        return selenium.isElementPresent(LOGIN_FORM_SUBMIT_LOCATOR);
    }
    
    // #################### Login ####################
    
    public void selectDomainName(String domainName) {
        selenium.type(LOGIN_FORM_DOMAIN_NAME_LOCATOR, domainName);
    }
    
    public void typeLoginId(String loginId) {
        selenium.type(LOGIN_FORM_LOGIN_ID_LOCATOR, loginId);
    }
    
    public void clickSubmit() {
        selenium.click(LOGIN_FORM_SUBMIT_LOCATOR);
        selenium.waitForPageToLoad("30000");
    }
}
