package com.hung.auction.restfuljaxrsresource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hung.auction.domain.Domain;
import com.hung.auction.jaxbdomain.JaxbDomain;
import com.hung.auction.service.DomainService;

@Component
@Path("/jaxrest/domains")	// host DomainResource at URI path '/domains'
public class RestfulDomainResource {

    private static Logger log = Logger.getLogger(RestfulDomainResource.class);

    /*
        @Autowired, @InjectParam("domainService"), and webAppContext.getBean("domainService") worked.
        1>for @Autowired to work, DomainServiceImpl need to be @Service
        2>HibernateDomainDAO need to be @Repository and @Autowired its constructor to call setSessionFactory(sessionFactory)
        3>@InjectParam("domainService") works too
    */
    // @InjectParam("domainService")
    /*
        @Qualifier("domainService") allow @Autowired to specific 'domainService' bean defined in applicationContext-dao.xml
        comment out for @Autowired to create fresh obj via @Service DomainServiceImpl and @Repository HibernateDomainDAO
    */
    // @Qualifier("domainService")
    @Autowired
    @Qualifier("domainService")
    private DomainService domainService;	// dont need setXXX method when autowire

    public RestfulDomainResource() {}

    // return list of domain
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<JaxbDomain> getAllDomains() {
        List<JaxbDomain> jaxbDomains = Collections.EMPTY_LIST;
        if (domainService != null) {
            List<Domain> domains = domainService.findAll(true);
            jaxbDomains = new ArrayList<JaxbDomain>(domains.size());
            for (int i=0; i<domains.size(); i++) {
                Domain domain = domains.get(i);
                jaxbDomains.add(new JaxbDomain(domain));
            }
        }
        return jaxbDomains;
    }

    // get a domain by name
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{name}")	// use GET '/domains/{name}' to retrieve JaxbDomain
    public JaxbDomain getDomainByName(@PathParam("name") String name) {
        JaxbDomain jaxbDomain = null;
        Domain domain = domainService.findByName(name, true);
        if (domain != null) {
            jaxbDomain = new JaxbDomain(domain);
        }
        return jaxbDomain;
    }

    /*
    Build a simple servlet where
    1>PUT creates a new domain,
    2>GET returns the domain information based on the domain key,
    3>DELETE deletes the domain
    4>POST updates the domain information
    */

    // create a domain via put
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JaxbDomain createDomainViaPut(JaxbDomain jaxbDomain) {
        Domain domain = new Domain(jaxbDomain.getName(), domainService.findByName(jaxbDomain.getParentDomainName()));
        domainService.save(domain);
        return new JaxbDomain(domain);
    }

    // create a domain via post
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JaxbDomain createDomainViaPost(JaxbDomain jaxbDomain) {
        Domain domain = new Domain(jaxbDomain.getName(), domainService.findByName(jaxbDomain.getParentDomainName()));
        domainService.save(domain);
        return new JaxbDomain(domain);
    }

    // update a domain
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{name}")	// use POST '/domains/{name}' to update JaxbDomain
    public Response updateDomainByName(@PathParam("name") String name, JaxbDomain jaxbDomain) {
        Domain domain = new Domain(name, domainService.findByName(jaxbDomain.getParentDomainName()));
        domainService.save(domain);
        // for both PUT and DELETE, you should send either 200 (Response.Status.OK) or 204 (Response.Status.NO_CONTENT).
        return Response.noContent().build();
    }

    // delete a domain
    @DELETE
    @Path("{name}")	// use DELETE '/domains/{name}' to delete JaxbDomain
    public Response deleteDomainByName(@PathParam("name") String name) {
        domainService.deleteByName(name);
        // for both PUT and DELETE, you should send either 200 (Response.Status.OK) or 204 (Response.Status.NO_CONTENT).
        return Response.noContent().build();
    }

    public void setDomainService(DomainService domainService) {
        this.domainService = domainService;
    }
}
