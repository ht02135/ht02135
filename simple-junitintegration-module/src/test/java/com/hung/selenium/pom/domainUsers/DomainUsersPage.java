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
    
    public static final String DOMAIN_USER_FORM_LOGIN_ID_LOCATOR = "css=form#jaxbDomainUser input#loginId";
    public static final String DOMAIN_USER_FORM_NAME_LOCATOR = "css=form#jaxbDomainUser input#name";
    public static final String DOMAIN_USER_FORM_DOMAIN_NAME_LOCATOR = "css=form#jaxbDomainUser select#userDomainName";
    public static final String DOMAIN_USER_FORM_SUBMIT_LOCATOR = "css=form#jaxbDomainUser input[name='submit'][type='submit']";
    
    public DomainUsersPage(MySelenium selenium) {
        super(selenium);
    }
    
    public void open() {
        super.open();
        
        // login
        super.selectDomainName("root");
        super.typeLoginId("admin");
        super.clickSubmit();
        
        // click domainUsers link
        log.info("selenium.isElementPresent(DOMAIN_USERS_LINK_LOCATOR)="+selenium.isElementPresent(DOMAIN_USERS_LINK_LOCATOR));
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
    
    public boolean isLoginIdFieldPresent() {
        return selenium.isElementPresent(DOMAIN_USER_FORM_LOGIN_ID_LOCATOR);
    }
    
    public boolean isNameFieldPresent() {
        return selenium.isElementPresent(DOMAIN_USER_FORM_NAME_LOCATOR);
    }
    
    public boolean isDomainNameFieldPresent() {
        return selenium.isElementPresent(DOMAIN_USER_FORM_DOMAIN_NAME_LOCATOR);
    }
    
    public boolean isSubmitButtonPresent() {
        return selenium.isElementPresent(DOMAIN_USER_FORM_SUBMIT_LOCATOR);
    }
    
    // action
    public void typeLoginId(String loginId) {
        selenium.type(DOMAIN_USER_FORM_LOGIN_ID_LOCATOR, loginId);
    }

    public void typeName(String name) {
        selenium.type(DOMAIN_USER_FORM_NAME_LOCATOR, name);
    }
    
    public void selectDomainName(String domainName) {
        selenium.select(DOMAIN_USER_FORM_DOMAIN_NAME_LOCATOR, domainName);
    }

    public void clickSubmit() {
        selenium.click(DOMAIN_USER_FORM_SUBMIT_LOCATOR);
        selenium.waitForPageToLoad("30000");
    }
    
    // verify
    public boolean isUserViewLinkPresent(String loginId) {
        return selenium.isElementPresent(String.format(DOMAIN_USERS_VIEW_LINK_LOCATOR, loginId));
    }
    
    public boolean isUserEditLinkPresent(String loginId) {
        return selenium.isElementPresent(String.format(DOMAIN_USERS_EDIT_LINK_LOCATOR, loginId));
    }
}
