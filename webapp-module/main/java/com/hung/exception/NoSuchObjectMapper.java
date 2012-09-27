package com.hung.auction.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
public class NoSuchObjectMapper implements ExceptionMapper<NoSuchObjectException> {

	private static Logger log = Logger.getLogger(NoSuchObjectMapper.class);

	public Response toResponse(NoSuchObjectException ex) {
		log.info("toResponse: enter");
		Response response = Response.status(Response.Status.NOT_FOUND).build();
		log.info("toResponse: exit, response="+response);
		return response;
	}
}
