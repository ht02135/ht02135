package com.hung.perf.webapp;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

// not real test, just play with HttpUnit API

/*
Unlike most commercial tools, HttpUnit does not rely on record and playback. Its API lets you define what you want 
to see or change, even before the web site is built. This is essential for test-first development. Since the 
primary use of HttpUnit is to write functional tests, it helps you abstract those elements which are most likely to 
change. For example, a page might start by providing a small number of options, using a set of radio buttons:

but i can see how selenium can speed up automating a test case faster than using httpunit.  cuz, try to navigate
link and inputing params via httpunit API in junit is a pain...  you achieve so little in amount of time that can
be accomplished by recording...
 */

public class HttpUnitIntegrationTest {
    
    private static Logger log = Logger.getLogger(HttpUnitIntegrationTest.class);
    
    public static final String AMAZON_ADDRESS = "http://www.amazon.com";
    public static final String FACEBOOK_ADDRESS = "https://www.facebook.com/";

    @Before
    public void setUp() { 
        // How do I turn off scripting for certain tests?
        HttpUnitOptions.setScriptingEnabled(false);
    }
    
    @After
    public void tearDown() {
    }
    
    // test http://www.amazon.com/
    @Test
    public void testAmazonMainPage() {
        try {
                WebConversation wc = new WebConversation();
                WebResponse response = wc.getResponse(AMAZON_ADDRESS);
                
                String expectedTitle = "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more";
                String actualTitle = response.getTitle();
                Assert.assertEquals(expectedTitle, actualTitle);
        } catch (SAXException e) {
            Assert.fail();
        } catch (IOException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testClickCart() throws Exception {
        WebConversation wc = new WebConversation();
        WebResponse response = wc.getResponse(AMAZON_ADDRESS);
        
        WebResponse cartPage = response.getLinkWith("Cart").click();
    }
    
    @Test
    public void testFacebookMainPage() throws Exception {
        WebConversation wc = new WebConversation();
        WebResponse response = wc.getResponse(FACEBOOK_ADDRESS);
        
        WebForm[] webForms = response.getForms();
        
        WebForm loginForm = webForms[0];
        String[] paramNames = loginForm.getParameterNames();
        for (int i=0; i<paramNames.length; i++) {
            String paramName = paramNames[i];
        }
        
        // this login form has many params, login will fail
        loginForm.setParameter("email", "blerr");
        loginForm.setParameter("pass", "blerr");
        WebResponse facebookMainPage = loginForm.submit();
        
        String expectedTitle = "Log In | Facebook";
        String actualTitle = facebookMainPage.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }
}
