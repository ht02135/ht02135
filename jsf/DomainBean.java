package com.hung.auction.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

@ManagedBean
@SessionScoped
public class DomainBean implements Serializable {	//DI via JSF managed bean

	private static Logger log = Logger.getLogger(DomainBean.class);
	
	private UIData domainTable;
	
	private String domainName = "";
	private Domain parentDomain = null;
	
	private List<Domain> domains = null;
	private List<SelectItem> domainItems = null;

	private DomainService domainService;	// DI via Spring
	
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
	
	public List<Domain> getDomains() {
		log.info("getDomains - entered");
		domains = domainService.findAll();
		
		log.info("getDomains - domains="+domains);
		return domains;
	}
	
    public List<SelectItem> getDomainItems() {
    	getDomains();
    	
    	domainItems = new ArrayList<SelectItem>();
        for (int i=0; i<domains.size(); i++) {
        	domainItems.add(new SelectItem(domains.get(i), domains.get(i).getName()));
        }

        return domainItems;
    }

	
	// ----------------------------------------------------------------------------------
	
	// navigate to addDomain.jsp
	public String addDomain() {
		log.info("addDomain - entered");
		return "success";
	}
	
	// for action attribute
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
	
	// for actionListener attribute
	public void createDomain(ActionEvent event) {
		log.info("createDomain (actionListener) - entered");
		createDomain();
	}
	
	public UIData getDomainTable() {
		return this.domainTable;
	}
	
	public void setDomainTable(UIData domainTable) {
		this.domainTable = domainTable;
	}
	
	// ---------------------------------------------------
	
	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}
}
