package com.hung.adaptor;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hung.auction.jaxbdomain.JaxbDomain;

public class RestfulDomainServiceAdaptor {

    private static Logger log = Logger.getLogger(RestfulDomainServiceAdaptor.class);

    private RestTemplate restTemplate = null;

    private static RestfulDomainServiceAdaptor instance = null;

    public static synchronized RestfulDomainServiceAdaptor getInstance() {
        if (instance == null) { instance = new RestfulDomainServiceAdaptor(); }
        return instance;
    }

    // Adaptor vs Proxy
    // Adaptor: it is used to adapt different interface.  it is more from client perspective.  adaptor expect
    //          interface to be of particular type and adaptor plays a role of filling that gap
    // Proxy: it is used to represent a standin object for a real object which is complex to create
    private RestfulDomainServiceAdaptor() {
        // domainService is an adaptee in Adaptor pattern
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
        restTemplate = (RestTemplate) applicationContext.getBean("restTemplate");
    }

    // get list of JaxbDomain
    public List<JaxbDomain> getAllDomains() {
        log.info("getAllDomains: enter");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<JaxbDomain[]> response = restTemplate.exchange(
            "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domains",
            HttpMethod.GET, entity, JaxbDomain[].class);

        List<JaxbDomain> jaxbDomains = Arrays.asList(response.getBody());
        log.info("getAllDomains: jaxbDomains="+jaxbDomains);
        log.info("getAllDomains: exit");

        return jaxbDomains;
    }

    public JaxbDomain getDomainByName(String name) {
        log.info("getDomainByName: enter, name="+name);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<JaxbDomain> response = restTemplate.exchange(
            "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domains/{name}",
            HttpMethod.GET, entity, JaxbDomain.class, name);

        JaxbDomain jaxbDomain = response.getBody();
        log.info("getDomainByName: name="+name+" jaxbDomain="+jaxbDomain);
        log.info("getDomainByName: exit");

        return jaxbDomain;
    }

    public JaxbDomain createDomainViaPut(JaxbDomain jaxbDomain) {
        log.info("createDomainViaPut: enter, jaxbDomain="+jaxbDomain);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<Object> entity = new HttpEntity<Object>(jaxbDomain, headers);

        ResponseEntity<JaxbDomain> response = restTemplate.exchange(
            "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domains",
            HttpMethod.PUT, entity, JaxbDomain.class);

        JaxbDomain createdJaxbDomain = response.getBody();
        log.info("createDomainViaPut: jaxbDomain="+jaxbDomain+" createdJaxbDomain="+createdJaxbDomain);
        log.info("createDomainViaPut: exit");

        return createdJaxbDomain;
    }

    public JaxbDomain createDomainViaPost(JaxbDomain jaxbDomain) {
        log.info("createDomainViaPost: enter, jaxbDomain="+jaxbDomain);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<Object> entity = new HttpEntity<Object>(jaxbDomain, headers);

        ResponseEntity<JaxbDomain> response = restTemplate.exchange(
            "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domains",
            HttpMethod.POST, entity, JaxbDomain.class);

        JaxbDomain createdJaxbDomain = response.getBody();
        log.info("createDomainViaPost: jaxbDomain="+jaxbDomain+" createdJaxbDomain="+createdJaxbDomain);
        log.info("createDomainViaPost: exit");

        return createdJaxbDomain;
    }

    public void updateDomain(JaxbDomain jaxbDomain) {
        log.info("updateDomain: enter, jaxbDomain="+jaxbDomain);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<Object> entity = new HttpEntity<Object>(jaxbDomain, headers);

        restTemplate.exchange(
            "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domains/{name}",
            HttpMethod.POST, entity, JaxbDomain.class, jaxbDomain.getName());

        log.info("updateDomain: exit");
    }

    public void deleteDomain(JaxbDomain jaxbDomain) {
        log.info("deleteDomain: enter, jaxbDomain="+jaxbDomain);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);

        restTemplate.exchange(
            "http://localhost:8081/simple-restfulwebapp-module/mvc/springrest/domains/{name}",
            HttpMethod.DELETE, entity, JaxbDomain.class, jaxbDomain.getName());

        log.info("deleteDomain: exit");
    }
}