package com.hung.auction.mvccontroller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

@Controller
@RequestMapping("/domains")
public class DomainsController implements IAuthenticateController {

	private static Logger log = Logger.getLogger(DomainsController.class);
	
	@Autowired
	private DomainService domainService;	// dont need setXXX method when autowire

	// display list of domains
	@RequestMapping(method=RequestMethod.GET)
	public String displayDomains(Model model) {
	
		// debug domains
		List<Domain> domains = domainService.findAll();
		log.info("displayDomains: domains="+domains);
		model.addAttribute("domains", domains);
		
		return "domains/list";
	}
	
	// view domain user of loginId
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public String viewDomain(@PathVariable String name, Model model) {
		log.info("viewDomain: enter, name="+name);
		
		Domain domain = domainService.findByName(name);
		log.info("viewDomain: domain="+domain);
	    model.addAttribute("domain", domain);
	    
	    return "domains/view";
	}
}
