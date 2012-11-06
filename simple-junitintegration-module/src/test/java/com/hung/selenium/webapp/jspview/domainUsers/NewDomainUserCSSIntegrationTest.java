package com.hung.selenium.webapp.jspview.domainUsers;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hung.utils.selenium.MySeleniumIntegrationTestFixture;

public class NewDomainUserCSSIntegrationTest extends MySeleniumIntegrationTestFixture {
    
    private static Logger log = Logger.getLogger(NewDomainUserCSSIntegrationTest.class);
    
    // here i want single selenium for this entire class
    @BeforeClass
    public static void setUpOnce() throws Exception { 
        gotoNewDomainUserCSS();
    }
    
    private static void gotoNewDomainUserCSS() throws Exception {
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
        
        // click domainUsers link
        selenium.click("css=a[href='/simple-restfulwebapp-module/auction/domainUsers']");
        selenium.waitForPageToLoad("30000");
        
        // clic new link
        selenium.click("css=a[href='/simple-restfulwebapp-module/auction/domainUsers?new']");
        selenium.waitForPageToLoad("30000");
    }
    
    // ########## Experiment with CSS ################################################################
    // http://www.w3.org/TR/CSS2/selector.html#attribute-selectors
    
    @Test
    public void testNewDomainUserIDSelector() {
        // E#I => match element E with id I
        
        // <input type="text" size="15" value="" name="loginId" id="loginId">
        Assert.assertTrue(selenium.isElementPresent("css=input#loginId"));  // multiple condition
        
        // <input type="text" size="15" value="" name="name" id="name">
        Assert.assertTrue(selenium.isElementPresent("css=input#name"));  // multiple condition
        
        // <select name="userDomainName" id="userDomainName"><option value="root">root</option><option value="subroot">subroot</option><option value="testSave">testSave</option></select>
        Assert.assertTrue(selenium.isElementPresent("css=select#userDomainName"));
    }
    
    @Test
    public void testNewDomainUserClassSelector() {
        // E.C => match element E with class C
        
        // <td><input name="submit" type="submit" value="submit" class="warning" /></td>
        Assert.assertTrue(selenium.isElementPresent("css=input.warning"));
    }
    
    @Test
    public void testNewDomainUserCombiningSelector() {
        int fieldCount = selenium.getCSSCount(":text#loginId , :text#name , select#userDomainName");
        Assert.assertEquals(3, fieldCount); // there should be 2 fields
    }
    
    @Test
    public void testNewDomainUserJQueryCustomFilterSelector() {
        // seems following selector are supported by CSS....
        
        // <input type="submit" class="warning" value="submit" name="submit">
        // :submit select input[type='submit'] or button[type='submit']
        Assert.assertTrue(selenium.isElementPresent("css=:submit")); 
        int submitCount = selenium.getCSSCount(":submit");
        Assert.assertEquals(1, submitCount);    // there should really be 1 submit button
        
        // this really narrow down
        Assert.assertTrue(selenium.isElementPresent("css=:submit[name='submit']"));
        submitCount = selenium.getCSSCount(":submit[name='submit']");
        Assert.assertEquals(1, submitCount);    // there should really be 1 submit button
        
        // :text select input[type='text'] and narrow down
        Assert.assertTrue(selenium.isElementPresent("css=:text#loginId"));
        int loginIdFieldCount = selenium.getCSSCount(":text#loginId");
        Assert.assertEquals(1, loginIdFieldCount);  // there should really be 1 loginId field
        
        Assert.assertTrue(selenium.isElementPresent("css=:text#name"));
        int nameFieldCount = selenium.getCSSCount(":text#name");
        Assert.assertEquals(1, nameFieldCount); // there should really be 1 name field
        
        // there shouldnt be any hiddenelement
        // :hidden select hidden element
        Assert.assertFalse(selenium.isElementPresent("css=:hidden"));
    }
    
    // ########## Functional test of domain users page ##################################################
    
    @Test
    public void testAddDomainUserForm() {
        // <form id="jaxbDomainUser" action="/simple-restfulwebapp-module/auction/domainUsers/add" method="post">  
        Assert.assertTrue(selenium.isElementPresent("css=form[id='jaxbDomainUser'][action='/simple-restfulwebapp-module/auction/domainUsers/add'][method='post']"));  // multiple condition
        
        // <input type="text" size="15" value="" name="loginId" id="loginId">
        Assert.assertTrue(selenium.isElementPresent("css=input[id='loginId'][type='text']"));  // multiple condition
        
        // <input type="text" size="15" value="" name="name" id="name">
        Assert.assertTrue(selenium.isElementPresent("css=input[id='name'][type='text']"));  // multiple condition
        
        // <select name="userDomainName" id="userDomainName"><option value="root">root</option><option value="subroot">subroot</option><option value="testSave">testSave</option></select>
        Assert.assertTrue(selenium.isElementPresent("css=select[id='userDomainName']"));
        
        // <input type="submit" value="submit" name="submit">
        Assert.assertTrue(selenium.isElementPresent("css=input[name='submit'][type='submit']"));  // multiple condition
    }
    
    @Test
    public void testUserDomainNameDropdownSelect() {
        // <form id="jaxbDomainUser" action="/simple-restfulwebapp-module/auction/domainUsers/add" method="post"> 
        // <select name="userDomainName" id="userDomainName"><option value="root">root</option><option value="subroot">subroot</option><option value="testSave">testSave</option></select>
        selenium.select("css=form#jaxbDomainUser select#userDomainName", "value=root");
        Assert.assertTrue(selenium.isSomethingSelected("css=form#jaxbDomainUser select#userDomainName"));
        
        String ActualValueOfSelectedItem = selenium.getValue("css=form#jaxbDomainUser select#userDomainName");
        log.info("ActualValueOfSelectedItem="+ActualValueOfSelectedItem);
        Assert.assertEquals("root", ActualValueOfSelectedItem);
    }
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
        selenium.close();
        selenium.stop();
    }
}
