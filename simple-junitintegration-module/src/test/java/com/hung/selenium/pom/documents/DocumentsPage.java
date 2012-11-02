package com.hung.selenium.pom.documents;

import org.apache.log4j.Logger;

import com.hung.selenium.pom.domainUsers.DomainUsersPage;
import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MySelenium;

public class DocumentsPage extends LoginPage {

    private static Logger log = Logger.getLogger(DomainUsersPage.class);
    
    public DocumentsPage(MySelenium selenium) {
        super(selenium);
    }
    
    public void open() {
        super.open();
        
        // login
        typeDomainName("root");
        typeLoginId("admin");
        clickSubmit();
        
        // click domainUsers link
        selenium.click("css=a[href='/simple-restfulwebapp-module/auction/documents']");
        selenium.waitForPageToLoad("30000");
    }
    
    public boolean isDocumentNameFieldPresent() {
        return selenium.isElementPresent("css=form#document input#name");
    }
    
    public boolean isDocumentFileFieldPresent() {
        return selenium.isElementPresent("css=form#document input#file");
    }
    
    public boolean isSubmitButtonPresent() {
        return selenium.isElementPresent("css=form#document :submit[type='submit']");
    }
    
    public boolean isDocumentAlreadyAdded(String name) {
        return selenium.isElementPresent("css=form#document a[href$='remove'][name='"+name+"']");
    }
    
    public void typeName(String name) {
        selenium.type("css=form#document input#name", name);
    }
    
    public void typeFile(String file) {
        selenium.type("css=form#document input#file", file);
    }
    
    public void clickSubmitFireEvent() {
        selenium.fireEvent("css=form#document :submit[type='submit']", "submit");
        selenium.click("css=form#document :submit[type='submit']"); 
    }
}
