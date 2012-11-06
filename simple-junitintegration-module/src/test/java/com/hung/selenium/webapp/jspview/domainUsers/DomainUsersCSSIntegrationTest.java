package com.hung.selenium.webapp.jspview.domainUsers;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hung.utils.selenium.MySeleniumIntegrationTestFixture;

public class DomainUsersCSSIntegrationTest extends MySeleniumIntegrationTestFixture {
    
    private static Logger log = Logger.getLogger(DomainUsersCSSIntegrationTest.class);
    
 // here i want single selenium for this entire class
    @BeforeClass
    public static void setUpOnce() throws Exception { 
        gotoDomainUsersCSS();
    }
    
    private static void gotoDomainUsersCSS() throws Exception {
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
    }
    
    // ########## Experiment with CSS ################################################################
    // http://www.w3.org/TR/CSS2/selector.html#attribute-selectors
    
    @Test
    public void testDomainUsersDirectChildSelector() {
        // E > F => match elements with tag name F that are direct child of E
        // html head title
        String title = selenium.getText("css=head > title");
        log.info("title="+title);
    }
    
    @Test
    public void testDomainUsersSubElementSelector() {
        // E F => match elements with tag name F that are descendent of E
        // html head title
        String title = selenium.getText("css=html title");
        log.info("title="+title);
    }
    
    @Test
    public void testDomainUsersAttributeSelector() {
        // check if admin view url exist
        // <a href="/simple-restfulwebapp-module/auction/domainUsers/admin">View</a>
        Assert.assertTrue(selenium.isElementPresent("css=a[href='/simple-restfulwebapp-module/auction/domainUsers/admin']"));
        
        // check if admin edit url exist
        // <a href="/simple-restfulwebapp-module/auction/domainUsers/admin?edit">Edit</a>
        Assert.assertTrue(selenium.isElementPresent("css=a[href='/simple-restfulwebapp-module/auction/domainUsers/admin?edit']"));
    }
    
    @Test
    public void testDomainUsersAttributeSubStringSelector() {
        // pre-fix, ^=
        int domainUserViewEditLinks = selenium.getCSSCount("a[href^='/simple-restfulwebapp-module/auction/domainUsers/']");
        // suffix, $=
        int domainUserEditLinks = selenium.getCSSCount("a[href$='?edit']");
        log.info("domainUserViewLinks="+domainUserViewEditLinks);
        log.info("domainUserEditLinks="+domainUserEditLinks);
        
        // domainUserViewEditLinks = be 2 * domainUserEditLinks
        Assert.assertEquals(domainUserViewEditLinks, domainUserEditLinks * 2);
        
        // match anywhere, *=
    }
    
    @Test
    public void testDomainUsersIDSelector() {
        
    }
    
    @Test
    public void testDomainUsersClassSelector() {
        
    }
    
    @Test
    public void testDomainUsersPseudoClasslSelector() { // or positional selector
        /*
            first-child, last-child, nth-child(1), example 
            1>body *:first-child
            2>body p:first-child
        */
    }
    
    // ########## Functional test of domain users page ##################################################
    
    @AfterClass
    public static void tearDownOnce() throws Exception {
        selenium.close();
        selenium.stop();
    }
}
