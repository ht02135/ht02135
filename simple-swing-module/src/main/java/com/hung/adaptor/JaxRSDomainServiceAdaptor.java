package com.hung.adaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.hung.auction.exception.NotOKStatusException;
import com.hung.auction.jaxbdomain.JaxbDomain;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class JaxRSDomainServiceAdaptor {

    private static Logger log = Logger.getLogger(JaxRSDomainServiceAdaptor.class);

    private Client jerseyClient = null;

    private static JaxRSDomainServiceAdaptor instance = null;

    public static synchronized JaxRSDomainServiceAdaptor getInstance() {
        if (instance == null) { instance = new JaxRSDomainServiceAdaptor(); }
        return instance;
    }

    private JaxRSDomainServiceAdaptor() {
        jerseyClient = Client.create();
        log.info("default constructor: jerseyClient="+jerseyClient);
    }

    public String getTestResult() {
        log.info("getTestResult: enter");
        String testResult = "";

        try {
            WebResource webResource = jerseyClient.resource("http://localhost:8081/simple-restfulwebapp-module/jersey/jaxrest").path("domains").path("test");
            log.info("getTestResult: webResource.getURI()="+webResource.getURI().toString());

            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);	// get XML from server via ClientResponse GET
            log.info("getTestResult: response.getStatus()="+response.getStatus());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                testResult = response.getEntity(String.class);
            } else {
                throw new NotOKStatusException(response.getStatus());
            }
        } catch (UniformInterfaceException uie) {
            log.error("getTestResult: uie="+uie);	// just log runtime exception and rethrow it
            throw uie;
        } catch (ClientHandlerException che) {
            log.error("getTestResult: che="+che);	// just log runtime exception and rethrow it
            throw che;
        }

        log.info("getTestResult: testResultStr="+testResult);
        log.info("getTestResult: exit");
        return testResult;
    }

    // get list of JaxbDomain
    public List<JaxbDomain> getAllDomains() {
        log.info("getAllDomains: enter");
        List<JaxbDomain> jaxbDomains = Collections.EMPTY_LIST;

        try {
            WebResource webResource = jerseyClient.resource("http://localhost:8081/simple-restfulwebapp-module/jersey/jaxrest").path("domains");
            log.info("getAllDomains: webResource.getURI()="+webResource.getURI().toString());

            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);	// get XML from server via ClientResponse GET
            log.info("getAllDomains: response.getStatus()="+response.getStatus());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                jaxbDomains = Arrays.asList(response.getEntity(JaxbDomain[].class));
            }
            /*	return empty list is better than throw exception?
            else {
                throw new NoSuchObjectException();
            }
            */
        } catch (UniformInterfaceException uie) {
            log.error("getAllDomains: uie="+uie);	// just log runtime exception and rethrow it
            throw uie;
        } catch (ClientHandlerException che) {
            log.error("getAllDomains: che="+che);	// just log runtime exception and rethrow it
            throw che;
        }

        log.info("getAllDomains: jaxbDomains="+jaxbDomains);
        log.info("getAllDomains: exit");

        return jaxbDomains;
    }

    public JaxbDomain getDomainByName(String name) {
        log.info("getDomainByName: enter, name="+name);
        JaxbDomain jaxbDomain = null;

        try {
            // use GET '/domains/{name}' to retrieve JaxbDomain
            WebResource webResource = jerseyClient.resource("http://localhost:8081/simple-restfulwebapp-module/jersey/jaxrest").path("domains").path(name);
            log.info("getDomainByName: webResource.getURI()="+webResource.getURI().toString());

            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).get(ClientResponse.class);	// get XML from server via ClientResponse GET
            log.info("getDomainByName: response.getStatus()="+response.getStatus());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                jaxbDomain = response.getEntity(JaxbDomain.class);
            }
            /* return null is better than throw exception?
            else {
                throw new NoSuchObjectException();
            }
            */
        } catch (UniformInterfaceException uie) {
            log.error("getDomainByName: uie="+uie);	// just log runtime exception and rethrow it
            throw uie;
        } catch (ClientHandlerException che) {
            log.error("getDomainByName: che="+che);	// just log runtime exception and rethrow it
            throw che;
        }

        log.info("getDomainByName: name="+name+" jaxbDomain="+jaxbDomain);
        log.info("getDomainByName: exit");

        return jaxbDomain;
    }

    public JaxbDomain createDomainViaPut(JaxbDomain jaxbDomain) {
        log.info("createDomainViaPost: enter, jaxbDomain="+jaxbDomain);
        JaxbDomain createdJaxbDomain = null;

        try {
            WebResource webResource = jerseyClient.resource("http://localhost:8081/simple-restfulwebapp-module/jersey/jaxrest").path("domains");
            log.info("createDomainViaPut: webResource.getURI()="+webResource.getURI().toString());

            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).put(ClientResponse.class, jaxbDomain);	// put XML to server via ClientResponse PUT
            log.info("createDomainViaPut: response.getStatus()="+response.getStatus());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                createdJaxbDomain = response.getEntity(JaxbDomain.class);
            } else {
                throw new NotOKStatusException(response.getStatus());
            }
        } catch (UniformInterfaceException uie) {
            log.error("createDomainViaPut: uie="+uie);	// just log runtime exception and rethrow it
            throw uie;
        } catch (ClientHandlerException che) {
            log.error("createDomainViaPut: che="+che);	// just log runtime exception and rethrow it
            throw che;
        }

        log.info("createDomainViaPut: jaxbDomain="+jaxbDomain+" createdJaxbDomain="+createdJaxbDomain);
        log.info("createDomainViaPut: exit");

        return createdJaxbDomain;
    }

    public JaxbDomain createDomainViaPost(JaxbDomain jaxbDomain) {
        log.info("createDomainViaPost: enter, jaxbDomain="+jaxbDomain);
        JaxbDomain createdJaxbDomain = null;

        try {
            WebResource webResource = jerseyClient.resource("http://localhost:8081/simple-restfulwebapp-module/jersey/jaxrest").path("domains");
            log.info("createDomainViaPost: webResource.getURI()="+webResource.getURI().toString());

            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, jaxbDomain);	// post XML to server via ClientResponse POST
            log.info("createDomainViaPost: response.getStatus()="+response.getStatus());
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                createdJaxbDomain = response.getEntity(JaxbDomain.class);
            } else {
                throw new NotOKStatusException(response.getStatus());
            }
        } catch (UniformInterfaceException uie) {
            log.error("createDomainViaPost: uie="+uie);	// just log runtime exception and rethrow it
            throw uie;
        } catch (ClientHandlerException che) {
            log.error("createDomainViaPost: che="+che);	// just log runtime exception and rethrow it
            throw che;
        }

        log.info("createDomainViaPost: jaxbDomain="+jaxbDomain+" createdJaxbDomain="+createdJaxbDomain);
        log.info("createDomainViaPost: exit");

        return createdJaxbDomain;
    }

    public void updateDomain(JaxbDomain jaxbDomain) {
        log.info("updateDomain: enter, jaxbDomain="+jaxbDomain);

        try {
            // use POST '/domains/{name}' to update JaxbDomain
            WebResource webResource = jerseyClient.resource("http://localhost:8081/simple-restfulwebapp-module/jersey/jaxrest").path("domains").path(jaxbDomain.getName());
            log.info("updateDomain: webResource.getURI()="+webResource.getURI().toString());

            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).post(ClientResponse.class, jaxbDomain);	// post XML to server via ClientResponse POST
            log.info("updateDomain: response.getStatus()="+response.getStatus());
            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                throw new NotOKStatusException(response.getStatus());
            }
        } catch (UniformInterfaceException uie) {
            log.error("updateDomain: uie="+uie);	// just log runtime exception and rethrow it
            throw uie;
        } catch (ClientHandlerException che) {
            log.error("updateDomain: che="+che);	// just log runtime exception and rethrow it
            throw che;
        }

        log.info("updateDomain: exit");
    }

    public void deleteDomain(JaxbDomain jaxbDomain) {
        log.info("deleteDomain: enter, jaxbDomain="+jaxbDomain);

        try {
            // use DELETE '/domains/{name}' to retrieve JaxbDomain
            WebResource webResource = jerseyClient.resource("http://localhost:8081/simple-restfulwebapp-module/jersey/jaxrest").path("domains").path(jaxbDomain.getName());
            log.info("deleteDomain: webResource.getURI()="+webResource.getURI().toString());

            ClientResponse response = webResource.accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);	// delete XML from server via ClientResponse DELETE
            log.info("deleteDomain: response.getStatus()="+response.getStatus());
            if (response.getStatus() != Response.Status.NO_CONTENT.getStatusCode()) {
                throw new NotOKStatusException(response.getStatus());
            }
        } catch (UniformInterfaceException uie) {
            log.error("deleteDomain: uie="+uie);	// just log runtime exception and rethrow it
            throw uie;
        } catch (ClientHandlerException che) {
            log.error("deleteDomain: che="+che);	// just log runtime exception and rethrow it
            throw che;
        }
        log.info("deleteDomain: exit");
    }

    protected void finalize() throws Throwable {
        jerseyClient.destroy();
    }
}