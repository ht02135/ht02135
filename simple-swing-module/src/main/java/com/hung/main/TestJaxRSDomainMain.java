package com.hung.main;

import java.util.List;

import org.apache.log4j.Logger;

import com.hung.adaptor.JaxRSDomainServiceAdaptor;
import com.hung.auction.jaxbdomain.JaxbDomain;
import com.hung.factory.DomainFactory;

public class TestJaxRSDomainMain {

	private static Logger log = Logger.getLogger(TestRestfulDomainMain.class);

    public static void main( String[] args )
    {
    	JaxRSDomainServiceAdaptor restfulDomainService = DomainFactory.getInstance().getJaxRSDomainServiceAdaptor();
    	
    	// debug service
    	String testResult = restfulDomainService.getTestResult();
    	log.info("main: testResult="+testResult);
    	
    	// ----------------------------------------------------------------------
    	// test list of domain
    	List<JaxbDomain> jaxbDomains = restfulDomainService.getAllDomains();
    	log.info("main: jaxbDomains="+jaxbDomains);
    	
    	// ----------------------------------------------------------------------
    	// find a domain by 'testDomain'
    	JaxbDomain jaxbDomain = restfulDomainService.getDomainByName("testDomain");
    	log.info("main: should exist -> jaxbDomain="+jaxbDomain);
    	
    	// ----------------------------------------------------------------------
    	// update domain's parent domain to root
    	jaxbDomain.setParentDomainName(JaxbDomain.ROOT_NAME);
    	restfulDomainService.updateDomain(jaxbDomain);
    	jaxbDomain = restfulDomainService.getDomainByName("testDomain");
    	log.info("main: should exist -> jaxbDomain="+jaxbDomain);

    	// ----------------------------------------------------------------------
    	jaxbDomain = restfulDomainService.getDomainByName("testDomain008");
    	if (jaxbDomain != null) {
    		log.info("main: need to delete found jaxbDomain="+jaxbDomain);
    		restfulDomainService.deleteDomain(jaxbDomain);
    	}
    	// create new domain via put
    	jaxbDomain = restfulDomainService.getDomainByName("testDomain008");
    	if (jaxbDomain == null) {
    		JaxbDomain newJaxbDomain = new JaxbDomain("testDomain008", JaxbDomain.ROOT_NAME);
    		JaxbDomain createdJaxbDomain = restfulDomainService.createDomainViaPut(newJaxbDomain);
    		log.info("main: newJaxbDomain="+newJaxbDomain+" createdJaxbDomain="+createdJaxbDomain);
    	} else {
    		log.info("main: failed to delete testDomain008");
    	}
    	
    	// ----------------------------------------------------------------------
    	jaxbDomain = restfulDomainService.getDomainByName("testDomain009");
    	if (jaxbDomain != null) {
    		log.info("main: need to delete found jaxbDomain="+jaxbDomain);
    		restfulDomainService.deleteDomain(jaxbDomain);
    	}
    	// create new domain via post
    	jaxbDomain = restfulDomainService.getDomainByName("testDomain009");
    	if (jaxbDomain == null) {
    		JaxbDomain newJaxbDomain = new JaxbDomain("testDomain009", JaxbDomain.ROOT_NAME);
    		JaxbDomain createdJaxbDomain = restfulDomainService.createDomainViaPost(newJaxbDomain);
    		log.info("main: newJaxbDomain="+newJaxbDomain+" createdJaxbDomain="+createdJaxbDomain);
    	} else {
    		log.info("main: failed to delete testDomain009");
    	}
    }
}