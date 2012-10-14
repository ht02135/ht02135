package com.hung.auction.jsfBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

@ManagedBean
@SessionScoped
public class ManagedDomain implements Serializable {   // Domain for CRUD operation

    private static Logger log = Logger.getLogger(ManagedDomain.class);

    private String domainName = "";
    private Domain parentDomain = null;
    
    private DomainService domainService;	// DI via Spring
    
    // properties ----------------------------------------------------------------------

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Domain getParentDomain() {
        log.info("getParentDomain - parentDomain="+parentDomain);
        return parentDomain;
    }

    public void setParentDomain(Domain parentDomain) {
        this.parentDomain = parentDomain;
        log.info("setParentDomainName - this.parentDomain="+this.parentDomain);
    }

    /*
        typically one uses an action method to execute some code after a button or link is 
        clicked and then possibly navigate based on the outcome of executed code.
    */
    // for action attribute --------------------------------------------------------------

    public String addDomain() {
        log.info("addDomain - entered");
        return "success";
    }

    public String createDomain() {
        log.info("createDomain (action) - entered");

        if (((domainName == null) || (domainName.equalsIgnoreCase(""))) || (parentDomain == null))
        {
            log.info("createDomain - failure, empty domainName or parentDomain");
            return "failure";
        }

        Domain domain = domainService.findByName(domainName);
        if (domain != null) {
            log.info("createDomain - failure, domainName="+domainName+" already added");
            return "failure";  // already created
        }

        domain = new Domain(domainName, parentDomain);
        log.info("createDomain - adding domain="+domain);
        domainService.save(domain);

        log.info("createDomain - success");
        return "success";
    }

    /*
        An actionlistener method compared to an action method does not return a String.  A good example 
        of actionlistener could be in response to clicking on a checkbox and having the actionlistener 
        code behind it change a visual attribute of a page such as render a component that was not 
        rendered before..
        
        for example, manipulating/rendering domainTable
    */
    // for actionListener attribute -----------------------------------------------------------
    
    public void createDomain(ActionEvent event) {
        log.info("createDomain (actionListener) - entered");
        createDomain();
    }

    // inject -----------------------------------------------------------------------------------

    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }
}
