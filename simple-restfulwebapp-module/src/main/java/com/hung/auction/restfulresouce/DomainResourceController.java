package com.hung.auction.restfulresouce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hung.auction.domain.Domain;
import com.hung.auction.jaxbdomain.JaxbDomain;
import com.hung.auction.service.DomainService;

@Component
@Path("/jaxrest/domains")	// host DomainResource at URI path '/domains'
public class DomainResourceController implements DomainResource {

    private static Logger log = Logger.getLogger(DomainResourceController.class);

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
    private DomainService domainService;	// dont need setXXX method when autowire

    @Context ServletContext servletContext;
    @Context UriInfo uriInfo;

    public DomainResourceController() {
        log.info("********** DomainResourceController: enter **********");
        log.info("********** DomainResourceController: exit **********");
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/test")	// use GET '/domains/test' to do some test
    public String getTestResult(@Context HttpServletRequest httpServletReq) {
        log.info("********** getTestResult: enter **********");
        String testResult = "";

        String absolutePath = uriInfo.getAbsolutePath().toString();
        String baseURI = uriInfo.getBaseUri().toString();
        String requestURI = uriInfo.getRequestUri().toString();
        testResult = "[absolutePath="+absolutePath+", baseURI="+baseURI+", requestURI="+requestURI+"]";
        log.info("getTestResult: testResult="+testResult);

        /*
            If you don't specify the contextConfigLocation context parameter, the ContextLoaderListener will look
            for a /WEB-INF/applicationContext.xml file to load. Once the context files are loaded, Spring creates
            a WebApplicationContext object based on the bean definitions and puts it into the ServletContext.
            All Java web frameworks are built on top of the Servlet API, so you can use the following code to
            get the ApplicationContext that Spring created.
        */
        /*
        HttpSession httpSession = httpServletReq.getSession(true);
        WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(httpSession.getServletContext());
        domainService = (DomainService) webAppContext.getBean("domainService");
        */

        log.info("********** getTestResult: exit **********");

        return testResult;
    }

    // return list of domain
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<JaxbDomain> getAllDomains() {
        log.info("********** getAllDomains: enter **********");
        List<JaxbDomain> jaxbDomains = Collections.EMPTY_LIST;

        if (domainService != null) {
            List<Domain> domains = domainService.findAll(true);
            jaxbDomains = new ArrayList<JaxbDomain>(domains.size());
            for (int i=0; i<domains.size(); i++) {
                Domain domain = domains.get(i);
                jaxbDomains.add(new JaxbDomain(domain));
            }
            log.info("getAllDomains: domains="+domains+" jaxbDomains="+jaxbDomains);
        } else {
            log.info("getAllDomains: domainService is null!!!");
        }

        log.info("********** getAllDomains: exit **********");

        return jaxbDomains;
    }

    // get a domain by name
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{name}")	// use GET '/domains/{name}' to retrieve JaxbDomain
    public JaxbDomain getDomainByName(@PathParam("name") String name) {
        log.info("********** getDomainByName: enter, name="+name+" **********");
        JaxbDomain jaxbDomain = null;
        Domain domain = domainService.findByName(name, true);
        if (domain != null) {
            jaxbDomain = new JaxbDomain(domain);
        }
        /*	return null is better than throw exception?
        else {
            throw new NoSuchObjectException();
        }
        */
        log.info("getDomainByName: name="+name+" domain="+domain+" jaxbDomain="+jaxbDomain);
        log.info("********** getDomainByName: exit **********");

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
        log.info("********** createDomainViaPut: enter, jaxbDomain="+jaxbDomain+" **********");
        Domain domain = new Domain(jaxbDomain.getName(), domainService.findByName(jaxbDomain.getParentDomainName()));
        domainService.save(domain);
        JaxbDomain createdJaxbDomain = new JaxbDomain(domain);
        log.info("createDomainViaPut: jaxbDomain="+jaxbDomain+" domain="+domain+" return createdJaxbDomain="+createdJaxbDomain);
        log.info("********** createDomainViaPut: exit **********");

        return createdJaxbDomain;
    }

    // create a domain via post
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public JaxbDomain createDomainViaPost(JaxbDomain jaxbDomain) {
        log.info("********** createDomainViaPost: enter, jaxbDomain="+jaxbDomain+" **********");
        Domain domain = new Domain(jaxbDomain.getName(), domainService.findByName(jaxbDomain.getParentDomainName()));
        domainService.save(domain);
        JaxbDomain createdJaxbDomain = new JaxbDomain(domain);
        log.info("createDomainViaPost: jaxbDomain="+jaxbDomain+" domain="+domain+" return createdJaxbDomain="+createdJaxbDomain);
        log.info("********** createDomainViaPost: exit **********");

        return createdJaxbDomain;
    }

    // update a domain
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{name}")	// use POST '/domains/{name}' to update JaxbDomain
    public Response updateDomainByName(@PathParam("name") String name, JaxbDomain jaxbDomain) {
        log.info("********** updateDomainByName: enter, jaxbDomain="+jaxbDomain+" **********");
        // need abstract factory pattern to abstract away this mess...
        Domain domain = new Domain(name, domainService.findByName(jaxbDomain.getParentDomainName()));
        domainService.save(domain);
        log.info("updateDomain: name="+name+" jaxbDomain="+jaxbDomain+" domain="+domain);
        log.info("********** updateDomainByName: exit **********");

        // for both PUT and DELETE, you should send either 200 (Response.Status.OK) or 204 (Response.Status.NO_CONTENT).
        return Response.noContent().build();
    }

    // delete a domain
    @DELETE
    @Path("{name}")	// use DELETE '/domains/{name}' to delete JaxbDomain
    public Response deleteDomainByName(@PathParam("name") String name) {
        log.info("********** deleteDomainByName: enter, name="+name+" **********");
        domainService.deleteByName(name);
        log.info("********** deleteDomainByName: exit **********");

        // for both PUT and DELETE, you should send either 200 (Response.Status.OK) or 204 (Response.Status.NO_CONTENT).
        return Response.noContent().build();
    }

    public void setDomainService(DomainService domainService) {
        log.info("********** setDomainService: enter domainService="+domainService+" **********");
        this.domainService = domainService;
        log.info("********** setDomainService: exit **********");
    }
}
