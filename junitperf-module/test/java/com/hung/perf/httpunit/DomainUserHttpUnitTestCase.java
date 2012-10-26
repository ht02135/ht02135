package com.hung.perf.httpunit;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class DomainUserHttpUnitTestCase extends TestCase {
    private static Logger log = Logger.getLogger(DomainUserHttpUnitTestCase.class);
    
    public static final String HOME_URL = "http://127.0.0.1:8081/simple-restfulwebapp-module/auction/";
    
    private WebConversation conversation;
    private WebResponse loggedInRequest;
    
    @Before
    public void setUp() { 
        // How do I turn off scripting for certain tests?
        HttpUnitOptions.setScriptingEnabled(false);
        
        conversation = new WebConversation();
        loggedInRequest = null;
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testLogin() {
        log.info("////////// testLogin: start //////////");
        try {
            // go to home
            WebRequest request = new GetMethodWebRequest(HOME_URL);
            WebResponse response = conversation.getResponse(request);
            
            // click on login link
            response = response.getLinkWith("Log In!").click();
            
            // get form with id=jaxbClientSession, and login with root/admin
            WebForm loginForm = response.getFormWithID("jaxbClientSession");
            loginForm.setParameter("domainName", "root");
            loginForm.setParameter("loginId", "admin");
            loggedInRequest = loginForm.submit();
            
            // verify logged-in
            String actualLoggedInHomeTitle = loggedInRequest.getTitle();
            String expectedLoggedInHomeTitle = "logged In Home";
            Assert.assertEquals(expectedLoggedInHomeTitle, actualLoggedInHomeTitle);
        } catch (Exception e) {
            log.info("e="+log);
        }
        log.info("////////// testLogin: end //////////");
    }
    
    @Test
    public void testCreateUser() {
        log.info("////////// testCreateUser: start //////////");
        try {
            // login first
            testLogin();
        
            // click on Domain Users
            WebResponse response = loggedInRequest.getLinkWith("Domain Users").click();
            
            // check if HttpUnitTest exists.  click "Create a new domain user" link
            // log.info("response.getText()="+response.getText());
            if (response.getText().indexOf("<td>HttpUnitTest</td>") > 0) {
                log.info("already contained <td>HttpUnitTest</td>");
            } else {
                response = response.getLinkWith("Create a new domain user").click();
                
                // get form with id=jaxbDomainUser, 
                WebForm createUserForm = response.getFormWithID("jaxbDomainUser");
                createUserForm.setParameter("loginId", "HttpUnitTest");
                createUserForm.setParameter("name", "HttpUnitTest");
                createUserForm.setParameter("userDomainName", "root");
                response = createUserForm.submit();
                
                // log.info("response.getText()="+response.getText());
                if (response.getText().indexOf("<td>HttpUnitTest</td>") > 0) {
                    log.info("<td>HttpUnitTest</td> successfully created");
                } else {
                    Assert.fail();
                }
            }
            
        } catch (Exception e) {
            log.info("e="+log);
        }
        log.info("////////// testCreateUser: end //////////");
    }
}
