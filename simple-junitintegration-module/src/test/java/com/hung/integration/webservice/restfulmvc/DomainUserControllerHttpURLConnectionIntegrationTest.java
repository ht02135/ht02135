package com.hung.integration.webservice.restfulmvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class DomainUserControllerHttpURLConnectionIntegrationTest {
    
    private static Logger log = Logger.getLogger(DomainUserControllerHttpURLConnectionIntegrationTest.class);
    
    public static final String DOMAIN_USERS_URI = "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domainusers%s";

    @Before
    public void setUp() {}
    
    // {"loginId":"admin","name":"admin","userDomainName":"root"}
    @Test
    public void testFindJSonAdmin() {
    	log.info("##### testFindJSonAdmin - start ###################################################");
        try {
            String mediaType = "application/json";
            String uri = String.format(DOMAIN_USERS_URI, "/admin");
            String JSONString = getDomainUser(mediaType, uri);
            
            log.info("##### jaxbDomainUser JSON - start ###################################################");
            log.info("JSONString="+JSONString);
            log.info("##### jaxbDomainUser JSON - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
        } 
        log.info("##### testFindJSonAdmin - end ###################################################");
        log.info("");
    }
    
    // <?xml version="1.0" encoding="UTF-8" standalone="yes"?><JaxbDomainUser loginId="admin"><name>admin</name><userDomainName>root</userDomainName></JaxbDomainUser>
    @Test
    public void testFindXMLAdmin() {
    	log.info("##### testFindXMLAdmin - start ###################################################");
        try {
            String mediaType = "application/xml";
            String uri = String.format(DOMAIN_USERS_URI, "/admin"); 
            String XMLString = getDomainUser(mediaType, uri);
            
            log.info("##### jaxbDomainUser XML - start ###################################################");
            log.info("XMLString="+XMLString);
            log.info("##### jaxbDomainUser XML - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
        }
        log.info("##### testFindXMLAdmin - end ###################################################");
        log.info("");
    }
    
    // ------------------------------------------------------------------------------------------------------
    
    // comment out, RESTful cant just return List<JaxbDomainUser>
    /*
    @Test
    public void testFindJSonUsers() {
    	log.info("##### testFindJSonUsers - start ###################################################");
        try {
            String mediaType = "application/json";
            String uri = String.format(DOMAIN_USERS_URI, "");
            String JSONString = getDomainUser(mediaType, uri);
            
            log.info("##### jaxbDomainUser JSON - start ###################################################");
            log.info("JSONString="+JSONString);
            log.info("##### jaxbDomainUser JSON - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
        } 
        log.info("##### testFindJSonUsers - end ###################################################");
        log.info("");
    }
    
    @Test
    public void testFindXMLUsers() {
    	log.info("##### testFindXMLUsers - start ###################################################");
        try {
            String mediaType = "application/xml";
            String uri = String.format(DOMAIN_USERS_URI, ""); 
            String XMLString = getDomainUser(mediaType, uri);
            
            log.info("##### jaxbDomainUser XML - start ###################################################");
            log.info("XMLString="+XMLString);
            log.info("##### jaxbDomainUser XML - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
        }
        log.info("##### testFindXMLUsers - end ###################################################");
        log.info("");
    }
    */
    
    // ------------------------------------------------------------------------------------------------------
    
    @Test
    public void testCreateJSonUser() {
    	log.info("##### testCreateJSonUser - start ###################################################");
        try {
            String mediaType = "application/json";
            String uri = String.format(DOMAIN_USERS_URI, "");
            File file = new File("src/test/resources/data/httpurlconnection/domainUsers.json");
            List<String> domainUsers = FileUtils.readLines(file);
            Iterator<String> iter = domainUsers.iterator();
            while (iter.hasNext()) {
            	String domainUser = iter.next();
            	String JSONString = createDomainUser(mediaType, uri, domainUser);
                log.info("##### jaxbDomainUser JSON - start ###################################################");
                log.info("JSONString="+JSONString);
                log.info("##### jaxbDomainUser JSON - end ###################################################");
            }
        } catch (Exception e) {
            log.error("e="+e);
        } 
        log.info("##### testCreateJSonUser - end ###################################################");
        log.info("");
    }
    
    @Test
    public void testCreateXMLUser() {
    	log.info("##### testCreateXMLUser - start ###################################################");
        try {
            String mediaType = "application/xml";
            String uri = String.format(DOMAIN_USERS_URI, "");
            File file = new File("src/test/resources/data/httpurlconnection/domainUsers.xml");
            List<String> domainUsers = FileUtils.readLines(file);
            Iterator<String> iter = domainUsers.iterator();
            while (iter.hasNext()) {
            	String domainUser = iter.next();
            	String XMLString = createDomainUser(mediaType, uri, domainUser);
                log.info("##### jaxbDomainUser XML - start ###################################################");
                log.info("XMLString="+XMLString);
                log.info("##### jaxbDomainUser XML - end ###################################################");
            }
        } catch (Exception e) {
            log.error("e="+e);
        } 
        log.info("##### testCreateXMLUser - end ###################################################");
        log.info("");
    }
    
    // ------------------------------------------------------------------------------------------------------
    
    public String getDomainUser(String mediaType, String uri) throws Exception {
        return send(uri, mediaType, "GET", null);
    }
    
    public String createDomainUser(String mediaType, String uri, String domainUser) throws Exception {
    	return send(uri, mediaType, "POST", domainUser);
    }
    
    public String send(String uri, String mediaType, String method, String data) throws Exception {
    	log.info("uri="+uri);
    	log.info("mediaType="+mediaType);
    	log.info("method="+method);
    	if (data != null) log.info("data="+data);
    	
        StringBuffer sb = new StringBuffer();
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("accept-charset", "UTF-8");
        conn.setRequestProperty("accept", mediaType);
        conn.setRequestProperty("content-type", mediaType);
        
        if (method.equals("POST")) {
        	conn.setUseCaches(false);
        	conn.setDoOutput(true); // Triggers POST.
        }

        if (method.equalsIgnoreCase("POST") && (data != null)) {
        	OutputStreamWriter writer = null;
        	try {
        	    writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
        	    writer.write(data); // Write POST query string (if any needed).
        	} finally {
        	    if (writer != null) try { writer.close(); } catch (IOException logOrIgnore) {}
        	}
        }
        
        if ((conn.getResponseCode() == 200) || (conn.getResponseCode() == 201)) {
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
