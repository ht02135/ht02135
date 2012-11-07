package com.hung.auction.restfulmvccontroller;

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

/*
REST support in Spring MVC

@Controller
    Use the @Controller annotation to annotate the class that will be the controller in MVC and handle the HTTP request.
@RequestMapping
    Use the @RequestMapping annotation to annotate the function that should handle certain HTTP methods, URIs, or HTTP headers. This annotation is the key to the Spring REST support. You change the method parameter to handle other HTTP methods.
@PathVariable
    A path variable in the URI could be injected as a parameter using the @PathVariable annotation.

Other useful annotations
    Use @RequestParam to inject a URL parameter into the method.
    Use @RequestHeader to inject a certain HTTP header into the method.
    Use @RequestBody to inject an HTTP request body into the method.
    Use @ResponseBody to return the content or object as the HTTP response body.
    Use HttpEntity<T> to inject into the method automatically if you provide it as a parameter.
    Use ResponseEntity<T> to return the HTTP response with your custom status or headers.
*/

@Controller
@RequestMapping("/springrest/domains")
public class RestfulDomainsController {

    private static Logger log = Logger.getLogger(RestfulDomainsController.class);

    @Autowired
    private DomainService domainService;	// dont need setXXX method when autowire

    // return list of domain
    @RequestMapping(method=RequestMethod.GET, headers="Accept=application/json, application/xml")
    public @ResponseBody List<JaxbDomain> getAllDomains(@RequestHeader("Accept") String accept) {
        log.info("********** getAllDomains: enter **********");
        log.info("getAllDomains: accept="+accept);

        List<Domain> domains = domainService.findAll(true);
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
        Domain domain = domainService.findByName(name, true);
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

    /*
        1>PUT puts a page at a specific URL. If there’s already a page there, it’s replaced in toto. If there’s no
          page there, a new one is created. This means it’s like a DELETE
        2>POST, however, really has no equivalent in SQL. POST sends some data to a specified URL. The server on the
          other end of this URL can do whatever it wants with this data. It can store it somewhere private. (HTTP 204
          NO CONTENT). It can store it in the page at the URL that was POSTed to (HTTP 205 RESET CONTENT). It can
          store it in a new page, in which case it returns the URL of that page in the Location field of the HTTP
          response header (HTTP 201 CREATED). It can use it as input for several different existing and new pages.
          It can throw the information away. It can insert, update, or delete records in a database (or all of the
          above). It can start brewing coffee (HTTP 202 ACCEPTED). It can start global thermonuclear war. POST is
          decidely non-side-effect free and non-idempotent.
        3>servers should keep control of their own URI spaces, so some servers may allow PUTs to existing URLs and
          not to novel ones (403 Forbidden)

        Why bother with PUT, because you are always greeted with 403.  lol, just use POST!!!
     */

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
    
    // ----- injection -------------
    
    public void setDomainService(DomainService domainService) {
    	this.domainService = domainService;
    }
}
