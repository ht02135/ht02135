package com.hung.selenium.webapp.jspview.documents;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hung.utils.selenium.MySeleniumIntegrationTestFixture;

public class AddDeleteDocumentCSSIntegrationTest extends MySeleniumIntegrationTestFixture {
    
    private static Logger log = Logger.getLogger(AddDeleteDocumentCSSIntegrationTest.class);
    
    // here i want single selenium for this entire class
    @Before
    public void setUp() throws Exception { 
        gotoDocumentFormCSS();
    }
    
    private void gotoDocumentFormCSS() throws Exception {
        selenium.start();
        
        // open
        selenium.open("http://127.0.0.1:8081/simple-restfulwebapp-module/auction/");
        
        // login
        String jsessionid = selenium.getCookieByName("JSESSIONID");
        selenium.click("css=a[href='/simple-restfulwebapp-module/auction/login;jsessionid="+jsessionid+"']");
        selenium.waitForPageToLoad("30000");
        selenium.type("css=input#domainName", "root");  // id=domainName
        selenium.type("css=input#loginId", "admin");    // id=loginId
        selenium.click("css=input[name='login'][type='submit']");      // multiple condition => name=login, type=submit
        selenium.waitForPageToLoad("30000");
        
        // click document manager link
        selenium.click("css=a[href='/simple-restfulwebapp-module/auction/documents']");
        selenium.waitForPageToLoad("30000");
        
        String title = selenium.getTitle();
        log.info("title="+title);
    }
    
    // ########## Functional test of domain users page ##################################################
    
    @Test
    public void testAddDocument() {
        // <a href="/simple-restfulwebapp-module/auction/documents/1?remove" name="data.xml">Delete</a>
        String nameField = "data.xml";
        String fileField = "file://C:/Software/Selenium/IntegrationTest/Data/data.xml";
        
        if (!selenium.isElementPresent("css=form#document a[href$='remove'][name='"+nameField+"']")) {
            selenium.type("css=form#document input#name", nameField);
            // mmm, attachFile doesnt work quite right.  use type to simulate user type worked
            // selenium.attachFile("css=form#document input#file", fileField);
            selenium.type("css=form#document input#file", fileField);
            
            selenium.fireEvent("css=form#document :submit[type='submit']", "submit");   // simulate submit event
            selenium.click("css=form#document :submit[type='submit']"); 
            Assert.assertFalse(selenium.isAlertPresent());
            
            selenium.waitForPageToLoad("30000");

            Assert.assertTrue(selenium.isTextPresent(nameField));
        } else {
            log.info("nameField="+nameField+" already added");
        }
    }
    
    @After
    public void tearDown() throws Exception {
        selenium.close();
        selenium.stop();
    }
}
