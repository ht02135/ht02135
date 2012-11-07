package com.hung.auction.restfulmvccontroller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hung.auction.domain.Domain;
import com.hung.auction.domain.DomainUser;
import com.hung.auction.jaxbdomain.JaxbDomainUser;
import com.hung.auction.service.DomainService;
import com.hung.auction.service.DomainUserService;

@Controller
@RequestMapping("/springrest/domainusers")
public class RestfulDomainUsersController {

    private static Logger log = Logger.getLogger(RestfulDomainUsersController.class);

    @Autowired
    private DomainService domainService;
    
    @Autowired
    private DomainUserService domainUserService;

    // return list of domainUser
    @RequestMapping(method=RequestMethod.GET, headers="Accept=application/json, application/xml")
    public @ResponseBody List<JaxbDomainUser> getAllDomainUsers() {
        
        List<DomainUser> domainUsers = domainUserService.findAll();
        List<JaxbDomainUser> jaxbDomainUsers = new ArrayList<JaxbDomainUser>(domainUsers.size());
        for (int i=0; i<domainUsers.size(); i++) {
            DomainUser domainUser = domainUsers.get(i);
            jaxbDomainUsers.add(new JaxbDomainUser(domainUser));
        }
        return jaxbDomainUsers;
    }

    // get a domainUser by loginId
    @RequestMapping(value="/{loginId}", method=RequestMethod.GET, headers="Accept=application/json, application/xml")
    public @ResponseBody JaxbDomainUser getDomainUserByLoginId(@PathVariable String loginId) {

        JaxbDomainUser jaxbDomainUser = null;
        DomainUser domainUser = domainUserService.findByLoginId(loginId);
        if (domainUser != null) {
            jaxbDomainUser = new JaxbDomainUser(domainUser);
        }
        return jaxbDomainUser;
    }

    // create a domainUser via post
    @RequestMapping(method=RequestMethod.POST, headers="Accept=application/json, application/xml")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody JaxbDomainUser createDomainUser(@RequestBody JaxbDomainUser jaxbDomainUser) {
        
        Domain domain = domainService.findByName(jaxbDomainUser.getUserDomainName());
        DomainUser domainUser = new DomainUser(jaxbDomainUser.getLoginId(), jaxbDomainUser.getName(), domain);
        domainUserService.save(domainUser);
        JaxbDomainUser createdJaxbDomainUser = new JaxbDomainUser(domainUser);
        return createdJaxbDomainUser;
    }

    // update a domainUser
    @RequestMapping(value="/{loginId}", method=RequestMethod.POST, headers="Accept=application/json, application/xml")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDomainUserByLoginId(@PathVariable String loginId, @RequestBody JaxbDomainUser jaxbDomainUser) {

        Domain domain = domainService.findByName(jaxbDomainUser.getUserDomainName());
        DomainUser domainUser = new DomainUser(jaxbDomainUser.getLoginId(), jaxbDomainUser.getName(), domain);
        domainUserService.save(domainUser);
    }

    @RequestMapping(value="/{loginId}", method=RequestMethod.DELETE, headers="Accept=application/json, application/xml")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDomainUserByLoginId(@PathVariable String loginId) {
        // do nothing for now
    }
}
