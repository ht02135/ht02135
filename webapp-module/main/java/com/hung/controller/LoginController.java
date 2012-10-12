package com.hung.auction.controller;

import javax.servlet.http.HttpSession;

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

/*
    1>@SessionAttributes annotation is used on the Controller class level to declare used session attributes. All Model 
    attributes having names or types defined in this annotation will be automatically persisted and restored between 
    the subsequent requests
    2>Strategy used for persisting Model attributes is defined by SessionAttributeStore implementation, Spring Framework 
    by default uses DefaultSessionAttributeStore which in turn relies on HttpSession as the storage (for Servlet environment).
    3>Model attributes are persisted at the end of request handling by AnnotationMethodHandlerAdapter, after calling your handler 
    method responsible for request handling and after determining the ModelAndView for the response. Their names in Model are 
    used as their names in the persistent storage
    4>Persisted Model attributes will be removed only if you'll call SessionStatus.setComplete() method in some of your 
    handler methods.
    
    RED-ALERT
    1>@SessionAttributes is not intended to be used (and will not work also) to store objects in the session between different controllers
    2>@SessionAttributes spring annotation declares session attributes. This will typically list the names of model attributes which should 
    be transparently stored in the session, serving as form-backing beans between subsequent requests. 
    3>@SessionAttribute is initialized when you put the corresponding attribute into model (either explicitly or using @ModelAttribute-annotated method).
    4>@SessionAttribute is updated by the data from HTTP parameters when controller method with the corresponding model attribute in its signature is invoked.
    5>@SessionAttributes are cleared when you call setComplete() on SessionStatus object passed into controller method as an argument.

    Hung:for me, jaxbClientSession is available across multiple controller.  i add it via LoginController and access in DomainUsersController.
 */

@Controller
@SessionAttributes({"jaxbClientSession"})
@RequestMapping("/login")
public class LoginController {

	private static Logger log = Logger.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	// populate jaxbClientSession (backing-bean) for the first time if its null
	@ModelAttribute("jaxbClientSession")
	public JaxbClientSession populateClientSession() {
		log.info("populateClientSession: populate jaxbClientSession for the first time if its null ");
		return new JaxbClientSession();
	}

	@RequestMapping(method=RequestMethod.GET)
	public String displayLogin(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, Model model) {
	    log.info("displayLogin: jaxbClientSession="+jaxbClientSession);
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
	public String login(@ModelAttribute("jaxbClientSession") JaxbClientSession jaxbClientSession, 
	                                    BindingResult bindingResult, Model model, HttpSession session)
	{
		log.info("********** login: start **********");
		log.info("login: jaxbClientSession="+jaxbClientSession);
		
		String url = "home";
		if (bindingResult.hasErrors()) {
			log.info("login: bindingResult hasErrors");
		} else {
			if (loginService.login(jaxbClientSession.getLoginId(), jaxbClientSession.getDomainName()).booleanValue()) {
			    session.setAttribute("jaxbClientSession2", jaxbClientSession);
			    url = "loggedInHome";
				log.info("login: successfully loggedIn, jaxbClientSession="+jaxbClientSession);
			} else {
				log.info("login: failed to login, jaxbClientSession="+jaxbClientSession);
			}
		}
		log.info("********** login: end **********");

		return url;
	}
	
	/*
        1>SessionStatus.setComplete() method will trigger cleaning of Session Attributes, but only those which Spring 
        will find "actual session attribute"
        2>Only the "jaxbClientSession" attribute will be considered as "actual session attribute", and removed on POST request

    @RequestMapping(method = RequestMethod.POST)
    public void onPost(@ModelAttribute("jaxbClientSession") String something, BindingResult bindingResult, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
    }
    */
	    
	// injection methods

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
}
