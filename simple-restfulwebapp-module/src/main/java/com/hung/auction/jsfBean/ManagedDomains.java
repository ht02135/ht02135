package com.hung.auction.jsfBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIData;

import org.apache.log4j.Logger;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

@ManagedBean
@SessionScoped
public class ManagedDomains implements Serializable {  // Domains for list/search operation

    private static Logger log = Logger.getLogger(ManagedDomains.class);
    
    private UIData domainTable;
    
    private DomainService domainService;	// DI via Spring
    
    // properties ----------------------------------------------------------------------
    
    public List<Domain> getDomains() {
        return domainService.findAll();
    }

    // binding JSF UI element ------------------------------------------------------------------
    
    public UIData getDomainTable() {
        return this.domainTable;
    }

    public void setDomainTable(UIData domainTable) {
        this.domainTable = domainTable;
    }

    // inject -----------------------------------------------------------------------------------

    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }
}
