package com.hung.selenium.pom.documents;

import org.apache.log4j.Logger;

import com.hung.selenium.pom.domainUsers.DomainUsersPage;
import com.hung.selenium.pom.login.LoginPage;
import com.hung.utils.selenium.MySelenium;

public class DocumentsPage extends LoginPage {

    private static Logger log = Logger.getLogger(DomainUsersPage.class);
    
    public static final String DOCUMENTS_LINK_LOCATOR = "css=a[href='/simple-restfulwebapp-module/auction/documents']";
    
    // link
    public static final String DOCUMENTS_REMOVE_LINK_LOCATOR = "css=form#document a[href$='remove'][name='%s']";
    
    // form
    public static final String DOCUMENT_FORM_LOCATOR = "css=form#document";
    public static final String DOCUMENT_FORM_NAME_LOCATOR = "css=form#document input#name";
    public static final String DOCUMENT_FORM_FILE_LOCATOR = "css=form#document input#file";
    public static final String DOCUMENT_FORM_SUBMIT_LOCATOR = "css=form#document :submit[type='submit']";
    
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
        selenium.click(DOCUMENTS_LINK_LOCATOR);
        selenium.waitForPageToLoad("30000");
    }
    
    // form
    public boolean isDocumentFormPresent() {
        return selenium.isElementPresent(DOCUMENT_FORM_LOCATOR);
    }
    
    public boolean isDocumentNameFieldPresent() {
        return selenium.isElementPresent(DOCUMENT_FORM_NAME_LOCATOR);
    }
    
    public boolean isDocumentFileFieldPresent() {
        return selenium.isElementPresent(DOCUMENT_FORM_FILE_LOCATOR);
    }
    
    public boolean isSubmitButtonPresent() {
        return selenium.isElementPresent(DOCUMENT_FORM_SUBMIT_LOCATOR);
    }
    
    // action
    public boolean isDocumentAlreadyAdded(String name) {
        return selenium.isElementPresent(String.format(DOCUMENTS_REMOVE_LINK_LOCATOR, name));
    }
    
    public void typeName(String name) {
        selenium.type(DOCUMENT_FORM_NAME_LOCATOR, name);
    }
    
    public void typeFile(String file) {
        selenium.type(DOCUMENT_FORM_FILE_LOCATOR, file);
    }
    
    public void clickSubmitFireEvent() {
        selenium.fireEvent(DOCUMENT_FORM_SUBMIT_LOCATOR, "submit");
        selenium.click(DOCUMENT_FORM_SUBMIT_LOCATOR); 
    }
}
