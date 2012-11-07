package com.hung.selenium2webdriver.pom.domainUsers;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hung.selenium2webdriver.pom.AbstractWDPage;
import com.hung.selenium2webdriver.pom.HomeWDPage;
import com.hung.selenium2webdriver.pom.login.LoginWDPage;

public class DomainUsersWDPage extends AbstractWDPage {

	private static Logger log = Logger.getLogger(DomainUsersWDPage.class);
	
    public static final String DOMAIN_USERS_LINK_LOCATOR = "a[href='/simple-restfulwebapp-module/auction/domainUsers']";
    
    // links
    public static final String DOMAIN_USERS_VIEW_LINK_LOCATOR = "table a[href='/simple-restfulwebapp-module/auction/domainUsers/%s']";
    public static final String DOMAIN_USERS_EDIT_LINK_LOCATOR = "table a[href='/simple-restfulwebapp-module/auction/domainUsers/%s?edit']";
    public static final String DOMAIN_USERS_NEW_LINK_LOCATOR = "table a[href='/simple-restfulwebapp-module/auction/domainUsers?new']";
    
    // form
    // later add form fields
    public static final String DOMAIN_USER_FORM_LOCATOR = "form#jaxbDomainUser";
    public static final String DOMAIN_USER_FORM_LOGIN_ID_LOCATOR = "form#jaxbDomainUser input#loginId";
    public static final String DOMAIN_USER_FORM_NAME_LOCATOR = "form#jaxbDomainUser input#name";
    public static final String DOMAIN_USER_FORM_DOMAIN_NAME_LOCATOR = "form#jaxbDomainUser select#userDomainName";
    public static final String DOMAIN_USER_FORM_SUBMIT_LOCATOR = "form#jaxbDomainUser input[name='submit'][type='submit']";
	
    public DomainUsersWDPage(WebDriver driver, String baseURL) {
    	super(driver, baseURL);
    }
    
    public void visit() {
    	HomeWDPage homeWDPage = new HomeWDPage(driver, baseURL);
    	homeWDPage.visit();	// first visit homepage
    	
    	LoginWDPage loginWDPage = new LoginWDPage(driver, baseURL);
    	loginWDPage.visit();	// then visit login page
    	loginWDPage.login("root", "admin");
    	log.info("after login");
    	
    	getDomainUsersLink().click();
    	explicitWaitUntilWebElementIsVisible(DOMAIN_USERS_NEW_LINK_LOCATOR, WAIT_TIME_IN_SECONDS);	// explicit wait
    	log.info("after clicked domain users link");
    }
    
    public WebElement getDomainUsersLink() {
    	return driver.findElement(By.cssSelector(DOMAIN_USERS_LINK_LOCATOR));
    }
    
    public WebElement getNewDomainUserLink() {
    	return driver.findElement(By.cssSelector(DOMAIN_USERS_NEW_LINK_LOCATOR));
    }
    
    public WebElement getCreateDomainUserForm() {
    	return driver.findElement(By.cssSelector(DOMAIN_USER_FORM_LOCATOR));
    }
    
    public WebElement getLoginIdInput() {
    	return driver.findElement(By.cssSelector(DOMAIN_USER_FORM_LOGIN_ID_LOCATOR));
    }
    
    public WebElement getNameInput() {
    	return driver.findElement(By.cssSelector(DOMAIN_USER_FORM_NAME_LOCATOR));
    }
    
    public WebElement getDomainNameSelect() {
    	return driver.findElement(By.cssSelector(DOMAIN_USER_FORM_DOMAIN_NAME_LOCATOR));
    }
    
    public WebElement getSubmitInput() {
    	return driver.findElement(By.cssSelector(DOMAIN_USER_FORM_SUBMIT_LOCATOR));
    }
    
    public void createUser(String loginId, String name, String domainName) {
    	getLoginIdInput().clear();
    	getLoginIdInput().sendKeys(loginId);
    	getNameInput().clear();
    	getNameInput().sendKeys(name);
    	clickSelectOption(getDomainNameSelect(), domainName);
    	getSubmitInput().submit();
    	
    	// implicit wait
    	implicitWait(WAIT_TIME_IN_SECONDS);
    }
    
    public void clickNewDomainUserLink() {
    	getNewDomainUserLink().click();
    	explicitWaitUntilWebElementIsVisible(DOMAIN_USER_FORM_LOCATOR, WAIT_TIME_IN_SECONDS);	// explicit wait
    }
}
