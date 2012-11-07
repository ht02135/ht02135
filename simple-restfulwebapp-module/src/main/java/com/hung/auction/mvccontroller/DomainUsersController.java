package com.hung.auction.mvccontroller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hung.auction.domain.Domain;
import com.hung.auction.domain.DomainUser;
import com.hung.auction.jaxbdomain.JaxbClientSession;
import com.hung.auction.jaxbdomain.JaxbDomainUser;
import com.hung.auction.service.DomainService;
import com.hung.auction.service.DomainUserService;
import com.hung.auction.service.LoginService;

@Controller
@SessionAttributes({"jaxbClientSession"})
@RequestMapping("/domainUsers")
public class DomainUsersController implements IAuthenticateController {

	private static Logger log = Logger.getLogger(DomainUsersController.class);
	
	@Autowired
	private DomainUserService domainUserService;	// dont need setXXX method when autowire
	
	@Autowired
	private DomainService domainService;	// dont need setXXX method when autowire
	
	@Autowired
	private LoginService loginService;	// dont need setXXX method when autowire

	// display list of domain users
	@RequestMapping(method=RequestMethod.GET)
	public String displayDomainUsers(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, Model model) {
		log.info("displayDomainUsers: enter");			
		
		// check if we still have jaxbClientSession from login process
		checkClientSession(jaxbClientSession);
		
		// debug domainUsers
		List<DomainUser> domainUsers = domainUserService.findAll();
		log.info("displayDomainUsers: domainUsers="+domainUsers);
		model.addAttribute("domainUsers", domainUsers);
		
		return "domainUsers/list";
	}
	
	// create a new domain user.  params=new is passed by ?new
	@RequestMapping(method=RequestMethod.GET, params="new")
	public String createDomainUser(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, Model model) {
		log.info("createDomainUser: enter");
		
		// check if we still have jaxbClientSession from login process
		checkClientSession(jaxbClientSession);
		
		// create an empty domain user and place it in edit page
		List<Domain> domains = domainService.findAll();
		List<String> domainNames = new ArrayList<String>(domains.size());
		for (int i=0; i<domains.size(); i++) {
			domainNames.add(domains.get(i).getName());
		}
		model.addAttribute("domainNames", domainNames);
		log.info("createDomainUser: domainNames="+domainNames);
		
		model.addAttribute("jaxbDomainUser", new JaxbDomainUser());
		model.addAttribute("action", "add");	// jsp page form will using this to set 'action' attribute
		
		return "domainUsers/edit";
	}
	
	// view domain user of loginId
	@RequestMapping(value="/{loginId}", method=RequestMethod.GET)
	public String viewDomainUser(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, @PathVariable String loginId, Model model) {
		log.info("viewDomainUser: enter, loginId="+loginId);
		
		// check if we still have jaxbClientSession from login process
		checkClientSession(jaxbClientSession);
		
		DomainUser domainUser = domainUserService.findByLoginId(loginId);
		log.info("viewDomainUser: domainUser="+domainUser);
	    model.addAttribute("domainUser", domainUser);
	    
	    return "domainUsers/view";
	}
	
	// edit a domain user.  params=edit is passed by ?edit
	@RequestMapping(value="/{loginId}", method=RequestMethod.GET, params="edit")
	public String editDomainUser(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, @PathVariable String loginId, Model model) {
		log.info("editDomainUser: enter, loginId="+loginId);
		
		// check if we still have jaxbClientSession from login process
		checkClientSession(jaxbClientSession);
		
		// get domain user from domainUserService and place it in edit page
		List<Domain> domains = domainService.findAll();
		List<String> domainNames = new ArrayList<String>(domains.size());
		for (int i=0; i<domains.size(); i++) {
			domainNames.add(domains.get(i).getName());
		}
		model.addAttribute("domainNames", domainNames);
		log.info("editDomainUser: domainNames="+domainNames);
		
		DomainUser domainUser = domainUserService.findByLoginId(loginId);
		log.info("editDomainUser: domainUser="+domainUser);
		
	    model.addAttribute("jaxbDomainUser", new JaxbDomainUser(domainUser));
	    model.addAttribute("action", "edit");	// jsp page form will using this to set 'action' attribute
	    
	    return "domainUsers/edit";
	}
	
	// handle create domain user request from jsp page
	// value=/add map to jsp page form action=/domainUsers/add
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addDomainUserFromForm(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, 
	                                    @ModelAttribute("jaxbDomainUser") @Valid JaxbDomainUser jaxbDomainUser, 
										BindingResult bindingResult, Model model)
	{
		log.info("********** addDomainUserFromForm: start, jaxbDomainUser="+jaxbDomainUser+" **********");
		
		// check if we still have jaxbClientSession from login process
		checkClientSession(jaxbClientSession);
		
		String url = "domainUsers/edit";
	    if (bindingResult.hasErrors()) {
	    	log.info("addDomainUserFromForm: bindingResult hasErrors");
	    	model.addAttribute("action", "add");
	    } else {
	    	Domain domain = domainService.findByName(jaxbDomainUser.getUserDomainName());
	    	DomainUser domainUser = new DomainUser(jaxbDomainUser.getLoginId(), jaxbDomainUser.getName(), domain);
	    	log.info("addDomainUserFromForm: domainUser="+domainUser);
	    	
	    	domainUserService.save(domainUser);
	    	url = "redirect:/auction/domainUsers/" + domainUser.getLoginId();
	    }
	    log.info("addDomainUserFromForm: url="+url);
	    
	    log.info("********** addDomainUserFromForm: end, url="+url+" **********");
	    
	    return url;
	}
	
	// handle update domain user request from jsp page
	// value=/edit map to jsp page form action=/domainUsers/edit
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String updateDomainUserFromForm(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, 
	                                       @ModelAttribute("jaxbDomainUser") @Valid JaxbDomainUser jaxbDomainUser,
										   BindingResult bindingResult, Model model) 
	{
		log.info("********** updateDomainUserFromForm: start, jaxbDomainUser="+jaxbDomainUser+" **********");
		
		// check if we still have jaxbClientSession from login process
		checkClientSession(jaxbClientSession);
		
		String url = "domainUsers/edit";
	    if (bindingResult.hasErrors()) {
	    	log.info("updateDomainUserFromForm: bindingResult hasErrors");
	    	model.addAttribute("action", "edit");
	    } else {
	    	Domain domain = domainService.findByName(jaxbDomainUser.getUserDomainName());
	    	DomainUser domainUser = new DomainUser(jaxbDomainUser.getLoginId(), jaxbDomainUser.getName(), domain);
	    	log.info("updateDomainUserFromForm: domainUser="+domainUser);
	    	
	    	domainUserService.save(domainUser);
	    	url = "redirect:/auction/domainUsers/" + domainUser.getLoginId();
	    }
	    log.info("updateDomainUserFromForm: url="+url);
	    
	    log.info("********** updateDomainUserFromForm: end, url="+url+" **********");
	    
	    return url;
	}
	
	// both JaxbClientSession and ClientSession should be http session scope.  debug it
	private void checkClientSession(JaxbClientSession jaxbClientSession) {
		log.info("////////// DomainUsersController : checkClientSession - start //////////");
		
		if (jaxbClientSession == null) {
			log.info("DomainUsersController : checkClientSession: jaxbClientSession == null");
		} else {
			log.info("DomainUsersController : checkClientSession: jaxbClientSession="+jaxbClientSession);
		}
		
		log.info("////////// DomainUsersController : checkClientSession - end //////////");
	}
}
