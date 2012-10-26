package com.hung.junit.application.controller.restfulmvc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class DomainUserControllerHttpURLConnectionIntegrationTest {
    
    private static Logger log = Logger.getLogger(DomainUserControllerHttpURLConnectionIntegrationTest.class);

    @Before
    public void setUp() {  
        
    }
    
    @Test
    public void testFindJSonAdmin() {
        try {
            String mediaType = "application/json";
            String uri = "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domainusers/admin";
            String loginId = "admin";   
            String JSONString = getDomainUser(mediaType, uri, loginId);
            
            log.info("##### jaxbDomainUser JSON - start ###################################################");
            log.info("JSONString="+JSONString);
            log.info("##### jaxbDomainUser JSON - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
            // Assert.fail();
        } 
    }
    
    @Test
    public void testFindXMLAdmin() {
        try {
            String mediaType = "application/xml";
            String uri = "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domainusers/admin";
            String loginId = "admin";   
            String XMLString = getDomainUser(mediaType, uri, loginId);
            
            log.info("##### jaxbDomainUser XML - start ###################################################");
            log.info("XMLString="+XMLString);
            log.info("##### jaxbDomainUser XML - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
            // Assert.fail();
        }
    }
    
    public String getDomainUser(String mediaType, String uriString, String loginId) throws Exception {
        StringBuffer sb = new StringBuffer();
        URL url = new URL(uriString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", mediaType);
        if (conn.getResponseCode() == 200) {
            log.info("Success : HTTP code :" + conn.getResponseCode());
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            conn.disconnect();
        } else {
            log.error("Failed : HTTP error code :" + conn.getResponseCode());
        }
        return sb.toString();
    }
}
