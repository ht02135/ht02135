package com.hung.selenium.webapp.jspview.documents;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hung.selenium.utils.MySeleniumIntegrationTestFixture;

public class NewDocumentCSSIntegrationTest extends MySeleniumIntegrationTestFixture {
    
    private static Logger log = Logger.getLogger(NewDocumentCSSIntegrationTest.class);
    
    @BeforeClass
    public static void setUpOnce() throws Exception { 
        gotoNewDocumentCSS();
    }
    
    private static void gotoNewDocumentCSS() throws Exception {
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
    }
    
    // ########## Functional test of domain users page ##################################################
    
    @Test
    public void testNewDocumentForm() {
        // <input type="text" value="" name="name" id="name">
        Assert.assertTrue(selenium.isElementPresent("css=form#document input#name"));
        
        // <input type="file" id="file" name="file">
        Assert.assertTrue(selenium.isElementPresent("css=form#document input#file"));
        
        // <input type="submit" value="Add Document">
        Assert.assertTrue(selenium.isElementPresent("css=form#document :submit[type='submit']"));
    }
    
    @Test
    public void testNewDocumentJavaScript() {
        selenium.fireEvent("css=form#document :submit[type='submit']", "submit");   // simulate submit event
        selenium.click("css=form#document :submit[type='submit']"); 
        
        boolean isAlert = selenium.isAlertPresent();
        Assert.assertTrue(isAlert);
        
        if (isAlert) {
            String alert = selenium.getAlert();
            //log.info("alert=|"+alert+"|");
            Assert.assertEquals("name : Required Field ", alert);
        }
    }
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
        selenium.close();
        selenium.stop();
    }
}
