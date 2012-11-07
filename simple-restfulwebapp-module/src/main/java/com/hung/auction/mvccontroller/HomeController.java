package com.hung.auction.mvccontroller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {
	private static Logger log = Logger.getLogger(HomeController.class);
  
	@RequestMapping(method=RequestMethod.GET)
	public String displayHome(Map<String, Object> model) {
		log.info("displayHome: enter");
		return "home";	// return 'home' tile definition
	}
}
