package com.hung.selenium.pom.domainUsers;

import org.apache.log4j.Logger;

import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MySelenium;

public class DomainUsersPage extends LoginPage {

    private static Logger log = Logger.getLogger(DomainUsersPage.class);
    
    public static final String DOMAIN_USERS_LINK_LOCATOR = "css=a[href='/simple-restfulwebapp-module/auction/domainUsers']";
    
    // links
    public static final String DOMAIN_USERS_VIEW_LINK_LOCATOR = "css=table a[href='/simple-restfulwebapp-module/auction/domainUsers/%s']";
    public static final String DOMAIN_USERS_EDIT_LINK_LOCATOR = "css=table a[href='/simple-restfulwebapp-module/auction/domainUsers/%s?edit']";
    public static final String DOMAIN_USERS_NEW_LINK_LOCATOR = "css=table a[href='/simple-restfulwebapp-module/auction/domainUsers?new']";
    
    // form
    // later add form fields
    public static final String DOMAIN_USER_FORM_LOCATOR = "css=form#jaxbDomainUser";
    
    public DomainUsersPage(MySelenium selenium) {
        super(selenium);
    }
    
    public void open() {
        super.open();
        
        // login
        typeDomainName("root");
        typeLoginId("admin");
        clickSubmit();
        
        // click domainUsers link
        selenium.click(DOMAIN_USERS_LINK_LOCATOR);
        selenium.waitForPageToLoad("30000");
    }
    
    // links
    public boolean isAdminViewLinkPresent() {
        return selenium.isElementPresent(String.format(DOMAIN_USERS_VIEW_LINK_LOCATOR, "admin"));
    }
    
    public boolean isAdminEditLinkPresent() {
        return selenium.isElementPresent(String.format(DOMAIN_USERS_EDIT_LINK_LOCATOR, "admin"));
    }
    
    public boolean isNewUserLinkPresent() {
        return selenium.isElementPresent(DOMAIN_USERS_NEW_LINK_LOCATOR);
    }
    
    public void clickNewUserLink() {
        selenium.click(DOMAIN_USERS_NEW_LINK_LOCATOR);
        selenium.waitForPageToLoad("30000");
    }
    
    // form
    public boolean isNewUserFormPresent() {
        return selenium.isElementPresent(DOMAIN_USER_FORM_LOCATOR);
    }
}
