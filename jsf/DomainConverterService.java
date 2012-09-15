package com.hung.auction.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hung.auction.domain.Domain;
import com.hung.auction.service.DomainService;

@Service("domainConverterService")
public class DomainConverterService implements Converter {	// spring bean
	
	private static Logger log = Logger.getLogger(DomainBean.class);
    
	@Autowired
	@Qualifier("domainService")	
	private DomainService domainService;	// DI via Spring
	
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	Domain domain = domainService.findByName(value);
    	log.info("getAsObject - domain="+domain);
        return domain;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
    	Domain domain = (Domain) value;
    	log.info("getAsString - domain="+domain);
        return domain.getName();
    }
    
	// ---------------------------------------------------
	
	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}
}
