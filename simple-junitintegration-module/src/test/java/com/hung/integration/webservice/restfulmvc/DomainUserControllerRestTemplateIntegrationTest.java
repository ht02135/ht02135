package com.hung.integration.webservice.restfulmvc;

import java.io.StringWriter;
import java.util.Collections;

import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.hung.auction.jaxbdomain.JaxbDomainUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext-test-mvc.xml"})
public class DomainUserControllerRestTemplateIntegrationTest {
    
    private static Logger log = Logger.getLogger(DomainUserControllerRestTemplateIntegrationTest.class);
    
    public static final String DOMAIN_USERS_URI = "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domainusers%s";

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;
    
    @Autowired
    @Qualifier("jaxbMarshaller")
    Marshaller jaxbMarshaller;
    
    ObjectMapper objectMapper;
    
    @Before
    public void setUp() {  
        objectMapper = new ObjectMapper();
    }
    
    @Test
    public void testFindJSonAdmin() {
    	log.info("##### testFindJSonAdmin - start ###################################################");
        try {
            MediaType mediaType = MediaType.APPLICATION_JSON;
            String uri = String.format(DOMAIN_USERS_URI, "/admin"); 
            JaxbDomainUser jaxbDomainUser = getDomainUser(restTemplate, mediaType, uri);

            log.info("##### jaxbDomainUser JSON - start ###################################################");
            String JSONString = objectMapper.writeValueAsString(jaxbDomainUser);
            log.info("JSONString="+JSONString);
            log.info("##### jaxbDomainUser JSON - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
        }
        log.info("##### testFindJSonAdmin - end ###################################################");
        log.info("");
    }
    
    @Test
    public void testFindXMLAdmin() {
    	log.info("##### testFindXMLAdmin - start ###################################################");
        try {
            MediaType mediaType = MediaType.APPLICATION_XML;
            String uri = String.format(DOMAIN_USERS_URI, "/admin"); 
            JaxbDomainUser jaxbDomainUser = getDomainUser(restTemplate, mediaType, uri);

            log.info("##### jaxbDomainUser XML - start ###################################################");
            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);
            jaxbMarshaller.marshal(jaxbDomainUser, streamResult);
            String XMLString = stringWriter.toString();
            log.info("XMLString="+XMLString);
            log.info("##### jaxbDomainUser XML - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
        }
        log.info("##### testFindXMLAdmin - end ###################################################");
        log.info("");
    }
    
    // -----------------------------------------------------------------------------------------------------------------
    
    // comment out, RESTful cant just return List<JaxbDomainUser>
    /*
    @Test
    public void testFindJSonUsers() {
    	log.info("##### testFindJSonUsers - start ###################################################");
        try {
            MediaType mediaType = MediaType.APPLICATION_JSON;
            String uri = String.format(DOMAIN_USERS_URI, ""); 
            JaxbDomainUser jaxbDomainUser = getDomainUser(restTemplate, mediaType, uri);

            log.info("##### jaxbDomainUser JSON - start ###################################################");
            String JSONString = objectMapper.writeValueAsString(jaxbDomainUser);
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
            MediaType mediaType = MediaType.APPLICATION_XML;
            String uri = String.format(DOMAIN_USERS_URI, ""); 
            JaxbDomainUser jaxbDomainUser = getDomainUser(restTemplate, mediaType, uri);

            log.info("##### jaxbDomainUser XML - start ###################################################");
            StringWriter stringWriter = new StringWriter();
            StreamResult streamResult = new StreamResult(stringWriter);
            jaxbMarshaller.marshal(jaxbDomainUser, streamResult);
            String XMLString = stringWriter.toString();
            log.info("XMLString="+XMLString);
            log.info("##### jaxbDomainUser XML - end ###################################################");
        } catch (Exception e) {
            log.error("e="+e);
        }
        log.info("##### testFindXMLUsers - end ###################################################");
        log.info("");
    }
    */
    
    // -----------------------------------------------------------------------------------------------------------------
    
    public JaxbDomainUser getDomainUser(RestTemplate restTemplate, MediaType mediaType, String uri) {
    	log.info("mediaType="+mediaType);
    	log.info("uri="+uri);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setAccept(Collections.singletonList(mediaType));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<JaxbDomainUser> response = restTemplate.exchange(uri, HttpMethod.GET, entity, JaxbDomainUser.class);
        return response.getBody();
    }
}
