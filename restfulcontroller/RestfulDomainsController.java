package com.hung.auction.restfulcontroller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hung.auction.domain.Domain;
import com.hung.auction.jaxbdomain.JaxbDomain;
import com.hung.auction.service.DomainService;

@Controller
@RequestMapping("/springrest/domains")
public class RestfulDomainsController {

	private static Logger log = Logger.getLogger(RestfulDomainsController.class);
	
	@Autowired
	private DomainService domainService;

	// return list of domain
	@RequestMapping(method=RequestMethod.GET, headers="Accept=application/json, application/xml")
	public @ResponseBody List<JaxbDomain> getAllDomains(@RequestHeader("Accept") String accept) {
		log.info("********** getAllDomains: enter **********");
		log.info("getAllDomains: accept="+accept);
		
		List<Domain> domains = domainService.findAll();
		List<JaxbDomain> jaxbDomains = new ArrayList<JaxbDomain>(domains.size());
		for (int i=0; i<domains.size(); i++) {
			Domain domain = domains.get(i);
			jaxbDomains.add(new JaxbDomain(domain));
		}
		
		log.info("getAllDomains: domains="+domains+" jaxbDomains="+jaxbDomains);
		log.info("********** getAllDomains: exit **********");
		
		return jaxbDomains;
	}
	
	// get a domain by name
	@RequestMapping(value="/{name}", method=RequestMethod.GET, headers="Accept=application/json, application/xml")
	public @ResponseBody JaxbDomain getDomainByName(@PathVariable String name, @RequestHeader("Accept") String accept) {
		log.info("********** getDomainByName: enter, name="+name+" **********");
		log.info("getDomainByName: accept="+accept);
		
		JaxbDomain jaxbDomain = null;
		Domain domain = domainService.findByName(name);
		if (domain != null) {
			jaxbDomain = new JaxbDomain(domain);
		}
		
		log.info("getDomainByName: name="+name+" domain="+domain+" jaxbDomain="+jaxbDomain);
		log.info("********** getDomainByName: exit **********");
		
		return jaxbDomain;
	}
	
	// create a domain via put
	@RequestMapping(method=RequestMethod.PUT, headers="Accept=application/json, application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody JaxbDomain createDomainViaPut(@RequestBody JaxbDomain jaxbDomain) {
		log.info("********** createDomainViaPut: enter, jaxbDomain="+jaxbDomain+" **********");
		Domain domain = new Domain(jaxbDomain.getName(), domainService.findByName(jaxbDomain.getParentDomainName()));
		domainService.save(domain);
		JaxbDomain createdJaxbDomain = new JaxbDomain(domain);
		log.info("createDomainViaPut: jaxbDomain="+jaxbDomain+" domain="+domain+" return createdJaxbDomain="+createdJaxbDomain);
		log.info("********** createDomainViaPut: exit **********");
		
		return createdJaxbDomain;
	}

	// create a domain via post
	@RequestMapping(method=RequestMethod.POST, headers="Accept=application/json, application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody JaxbDomain createDomainViaPost(@RequestBody JaxbDomain jaxbDomain) {
		log.info("********** createDomainViaPost: enter, jaxbDomain="+jaxbDomain+" **********");
		Domain domain = new Domain(jaxbDomain.getName(), domainService.findByName(jaxbDomain.getParentDomainName()));
		domainService.save(domain);
		JaxbDomain createdJaxbDomain = new JaxbDomain(domain);
		log.info("createDomainViaPost: jaxbDomain="+jaxbDomain+" domain="+domain+" return createdJaxbDomain="+createdJaxbDomain);
		log.info("********** createDomainViaPost: exit **********");
		
		return createdJaxbDomain;
	}
	
	// update a domain
	@RequestMapping(value="/{name}", method=RequestMethod.POST, headers="Accept=application/json, application/xml")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateDomainByName(@PathVariable String name, @RequestBody JaxbDomain jaxbDomain) {
		log.info("********** updateDomainByName: enter, jaxbDomain="+jaxbDomain+" **********");
		// need abstract factory pattern to abstract away this mess...
		Domain domain = new Domain(name, domainService.findByName(jaxbDomain.getParentDomainName()));
		domainService.save(domain);
		log.info("updateDomain: name="+name+" jaxbDomain="+jaxbDomain+" domain="+domain);
		log.info("********** updateDomainByName: exit **********");
	}
	
	@RequestMapping(value="/{name}", method=RequestMethod.DELETE, headers="Accept=application/json, application/xml")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDomainByName(@PathVariable String name) {
		log.info("********** deleteDomainByName: enter, name="+name+" **********");
		domainService.deleteByName(name);
		log.info("********** deleteDomainByName: exit **********");
	}
}
