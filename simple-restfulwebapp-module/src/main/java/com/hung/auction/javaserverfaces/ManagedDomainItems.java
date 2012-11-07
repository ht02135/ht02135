package com.hung.auction.javaserverfaces;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

@ManagedBean
@SessionScoped
public class ManagedDomainItems {
    private static Logger log = Logger.getLogger(ManagedDomainItems.class);

    private DomainService domainService;    // DI via Spring
    
    // ---------------------------------------------------
    
    public List<Domain> getDomains() {
        return domainService.findAll();
    }
    
    public List<SelectItem> getDomainItems() {
        List<Domain> domains = getDomains();

        List<SelectItem> domainItems = new ArrayList<SelectItem>();
        for (int i=0; i<domains.size(); i++) {
            domainItems.add(new SelectItem(domains.get(i), domains.get(i).getName()));
        }

        return domainItems;
    }
    
    // inject --------------------------------------------------------------------------

    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }
}
