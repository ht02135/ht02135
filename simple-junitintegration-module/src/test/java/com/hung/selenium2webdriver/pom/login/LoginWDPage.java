package com.hung.selenium2webdriver.pom.login;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hung.selenium2webdriver.pom.AbstractWDPage;
import com.hung.selenium2webdriver.pom.HomeWDPage;

public class LoginWDPage extends AbstractWDPage {
	
	private static Logger log = Logger.getLogger(LoginWDPage.class);
	
	public static final String LOGIN_LINK_LOCATOR = "a[href^='/simple-restfulwebapp-module/auction/login']";
	
	// form
	public static final String LOGIN_FORM_LOCATOR = "form#jaxbClientSession";
	public static final String LOGIN_FORM_DOMAIN_NAME_LOCATOR = "form#jaxbClientSession input#domainName";
	public static final String LOGIN_FORM_LOGIN_ID_LOCATOR = "form#jaxbClientSession input#loginId";
	public static final String LOGIN_FORM_SUBMIT_LOCATOR = "form#jaxbClientSession input[name='login'][type='submit']";

    public LoginWDPage(WebDriver driver, String baseURL) {
    	super(driver, baseURL);
    }
    
    public void visit() {
    	HomeWDPage homeWDPage = new HomeWDPage(driver, baseURL);
    	homeWDPage.visit();	// first visit homepage
    	
    	getLoginLink().click();
    	explicitWaitUntilWebElementIsVisible(LOGIN_FORM_LOCATOR, WAIT_TIME_IN_SECONDS);	// explicit wait
    	log.info("after clicked login link");
    }
    
    public WebElement getLoginLink() {
    	return driver.findElement(By.cssSelector(LOGIN_LINK_LOCATOR));
    }
    
    public WebElement getLoginForm() {
    	return driver.findElement(By.cssSelector(LOGIN_FORM_LOCATOR));
    }
    
    public WebElement getDomainNameInput() {
    	return driver.findElement(By.cssSelector(LOGIN_FORM_DOMAIN_NAME_LOCATOR));
    }
    
    public WebElement getLoginIdInput() {
    	return driver.findElement(By.cssSelector(LOGIN_FORM_LOGIN_ID_LOCATOR));
    }
    
    public WebElement getSubmitInput() {
    	return driver.findElement(By.cssSelector(LOGIN_FORM_SUBMIT_LOCATOR));
    }
    
    public void login(String domainName, String loginId) {
    	log.info("domainName="+domainName);
    	log.info("loginId="+loginId);
    	
    	getDomainNameInput().clear();	// need to clear before sendKeys.  otherwise, you just append
    	getDomainNameInput().sendKeys(domainName);
    	getLoginIdInput().clear();	// need to clear before sendKeys.  otherwise, you just append
    	getLoginIdInput().sendKeys(loginId);
    	getSubmitInput().submit();
    	log.info("after submit login");
    	
    	// implicit wait
    	implicitWait(WAIT_TIME_IN_SECONDS);
    }
}
