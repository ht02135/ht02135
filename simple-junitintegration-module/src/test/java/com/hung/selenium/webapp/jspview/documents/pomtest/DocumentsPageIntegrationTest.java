package com.hung.selenium.webapp.jspview.documents.pomtest;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hung.selenium.pom.documents.DocumentsPage;
import com.hung.selenium.utils.MyDefaultSelenium;
import com.hung.selenium.utils.MySelenium;

public class DocumentsPageIntegrationTest {
    
    private static Logger log = Logger.getLogger(DocumentsPageIntegrationTest.class);
    
    private MySelenium selenium;
    private DocumentsPage documentsPage;
    
    @Before
    public void setUp() throws IOException{
        selenium = new MyDefaultSelenium("localhost", 4444, "*chrome", "http://localhost:8081/simple-restfulwebapp-module/auction/");
        selenium.start();
        documentsPage = new DocumentsPage(selenium);
        documentsPage.open();
    }
    
    @Test
    public void testNewDocumentForm() {
        Assert.assertTrue(documentsPage.isDocumentNameFieldPresent());
        Assert.assertTrue(documentsPage.isDocumentFileFieldPresent());
        Assert.assertTrue(documentsPage.isSubmitButtonPresent());
    }
    
    @Test
    public void testSubmitNewDocumentJavaScript() {
        
        // click submit without filling name or file fields
        documentsPage.clickSubmitFireEvent();
        boolean isAlertPresent = documentsPage.isAlertPresent();
        Assert.assertTrue(isAlertPresent);
        
        if (isAlertPresent) {
            String alert = documentsPage.getAlert();
            Assert.assertEquals("name : Required Field ", alert);
        }
    }
    
    @Test
    public void testAddDocument() {
        String nameField = "data.xml";
        String fileField = "file://C:/Software/Data/data.xml";
        
        if (!documentsPage.isDocumentAlreadyAdded(nameField)) {
            documentsPage.typeName(nameField);
            documentsPage.typeFile(fileField);
            
            documentsPage.clickSubmitFireEvent();
            Assert.assertFalse(documentsPage.isAlertPresent());
            documentsPage.waitForPageToLoad("3000");
            
            Assert.assertTrue(documentsPage.isTextPresent(nameField));
        } else {
            Assert.assertTrue(documentsPage.isTextPresent(nameField));
        }
    }

    @After
    public void tearDown() {
        selenium.close();
        selenium.stop();
    }   
}
