package com.hung.auction.restfuljaxrsresource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.hung.auction.jaxbdomain.JaxbDomain;

public interface DomainResource {

	public String getTestResult(@Context HttpServletRequest httpServletReq);
	
	public List<JaxbDomain> getAllDomains();
	
	public JaxbDomain getDomainByName(@PathParam("name") String name);
	
	public JaxbDomain createDomainViaPut(JaxbDomain jaxbDomain);

	public JaxbDomain createDomainViaPost(JaxbDomain jaxbDomain);
	
	public Response updateDomainByName(@PathParam("name") String name, JaxbDomain jaxbDomain);
	
	public Response deleteDomainByName(@PathParam("name") String name);
}
