package com.hung.auction.restful.springmvc;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hung.auction.domain.Domain;
import com.hung.auction.jaxbdomain.JaxbDomain;
import com.hung.auction.service.DomainService;

@Controller
@RequestMapping("/restfuldomaincontroller")
public class RestfulDomainController {

    private static Logger log = Logger.getLogger(RestfulDomainController.class);

    @Autowired
    private DomainService domainService;

    @RequestMapping(method=RequestMethod.GET, headers="Accept=application/json, application/xml")
    public @ResponseBody List<JaxbDomain> getDomains() {
        log.info("getDomains - enter");
        
        List<Domain> domains = domainService.findAll(true);
        log.info("getDomains - domains="+domains);
        List<JaxbDomain> jaxbDomains = new ArrayList<JaxbDomain>(domains.size());
        for (int i=0; i<domains.size(); i++) {
            Domain domain = domains.get(i);
            jaxbDomains.add(new JaxbDomain(domain));
        }
        
        log.info("getDomains - jaxbDomains="+jaxbDomains);
        return jaxbDomains;
    }

    @RequestMapping(value="/{name}", method=RequestMethod.GET, headers="Accept=application/json, application/xml")
    public @ResponseBody JaxbDomain getDomainByName(@PathVariable String name) {
        log.info("getDomainByName - enter, name="+name);
        
        JaxbDomain jaxbDomain = null;
        Domain domain = domainService.findByName(name, true);
        log.info("getDomainByName - domain="+domain);
        if (domain != null) {
            jaxbDomain = new JaxbDomain(domain);
        }
        
        log.info("getDomainByName - jaxbDomain="+jaxbDomain);
        return jaxbDomain;
    }
    
    // setter
    
    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }
}
