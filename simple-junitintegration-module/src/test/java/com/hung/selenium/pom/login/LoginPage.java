package com.hung.selenium.pom.login;

import org.apache.log4j.Logger;

import com.hung.selenium.pom.HomePage;
import com.hung.utils.selenium.MySelenium;

public class LoginPage extends HomePage {
    
    private static Logger log = Logger.getLogger(LoginPage.class);
    
    public LoginPage(MySelenium selenium) {
        super(selenium);
    }
    
    public void open() {
        super.open();
        
        /*
        both works, but i like the partial match
        String jsessionid = selenium.getCookieByName("JSESSIONID");
        selenium.click("css=a[href='/simple-restfulwebapp-module/auction/login;jsessionid="+jsessionid+"']");
        */
        selenium.click("css=a[href^='/simple-restfulwebapp-module/auction/login']");
        selenium.waitForPageToLoad("30000");
    }
    
    // #################### Is Login form fields present ####################
    
    public boolean isDomainNameFieldPresent() {
        return selenium.isElementPresent("css=form#jaxbClientSession input#loginId");
    }
    
    public boolean isLoginIdFieldPresent() {
        return selenium.isElementPresent("css=form#jaxbClientSession input#loginId");
    }
    
    public boolean isSubmitButtonPresent() {
        return selenium.isElementPresent("css=form#jaxbClientSession input[name='login'][type='submit']");
    }
    
    // #################### Login ####################
    
    public void typeDomainName(String domainName) {
        selenium.type("css=form#jaxbClientSession input#domainName", domainName);
    }
    
    public void typeLoginId(String loginId) {
        selenium.type("css=form#jaxbClientSession input#loginId", loginId);
    }
    
    public void clickSubmit() {
        selenium.click("css=form#jaxbClientSession input[name='login'][type='submit']");
        selenium.waitForPageToLoad("30000");
    }
}
