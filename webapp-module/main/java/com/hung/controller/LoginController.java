package com.hung.auction.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hung.auction.jaxbdomain.JaxbClientSession;
import com.hung.auction.service.LoginService;

@Controller
@SessionAttributes({"jaxbClientSession"})
@RequestMapping("/login")
public class LoginController {

	private static Logger log = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	// populate jaxbClientSession for the first time if its null
	@ModelAttribute("jaxbClientSession")
	public JaxbClientSession populateClientSession() {
		log.info("populateClientSession: populate jaxbClientSession for the first time if its null ");
		return new JaxbClientSession();
	}

	@RequestMapping(method=RequestMethod.GET)
	public String displayLogin(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, Model model) {
		if (jaxbClientSession == null) {
			log.info("displayLogin: jaxbClientSession == null, add jaxbClientSession to model");
			model.addAttribute("jaxbClientSession", new JaxbClientSession());
		} else {
			log.info("displayLogin: jaxbClientSession="+jaxbClientSession);
		}
		return "login";	// return 'login' tile definition
	}
	
	/*
		1>Before invoking the handler method, Spring invokes all the methods that have @ModelAttribute annotation. 
		It adds the data returned by these methods to a temporary Map object. The data from this Map would be added 
		to the final Model after the execution of the handler method.
		
        2>Then it prepares to invoke the the handler method. To invoke this method, it has to resolve the arguments. 
        If the method has a parameter with @ModelAttribute, then it would search in the temporary Map object with 
        the value of @ModelAttribute. If it finds, then the value from the Map is used for the handler method parameter.
	*/
	
	@RequestMapping(method=RequestMethod.POST)
	public String login(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, BindingResult bindingResult, Model model)
	{
		log.info("********** login: start **********");
		log.info("login: jaxbClientSession.getDomainName()="+jaxbClientSession.getDomainName()+", jaxbClientSession.getLoginId()="+jaxbClientSession.getLoginId());
		
		String url = "home";
		if (bindingResult.hasErrors()) {
			log.info("login: bindingResult hasErrors");
		} else {
			if (loginService.login(jaxbClientSession.getLoginId(), jaxbClientSession.getDomainName()).booleanValue()) {
				log.info("login: successfully loggedIn, jaxbClientSession.getLoginId()="+jaxbClientSession.getLoginId());
				url = "loggedInHome";
			} else {
				log.info("login: failed to login");
			}
		}
		log.info("login: url="+url);
		log.info("********** login: end **********");

		return url;
	}
	    
	// injection methods

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
}
