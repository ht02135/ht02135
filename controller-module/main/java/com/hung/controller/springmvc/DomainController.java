package com.hung.auction.controller.springmvc;

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
@RequestMapping("/domaincontroller")
public class DomainController {

	private static Logger log = Logger.getLogger(DomainController.class);
	
	@Autowired
	private DomainService domainService;	

	@RequestMapping(method=RequestMethod.GET)
	public String getDomains(Model model) {
		List<Domain> domains = domainService.findAll();
		model.addAttribute("domains", domains);
		return "domainscontroller/list";
	}
	
	@RequestMapping(value="/{name}", method=RequestMethod.GET)
	public String getDomainByName(@PathVariable String name, Model model) {
		Domain domain = domainService.findByName(name);
	    model.addAttribute("domain", domain);
	    return "domainscontroller/view";
	}
	
	// setter
	
	public void setDomainService(DomainService domainService) {
	    this.domainService = domainService;
	}
}
