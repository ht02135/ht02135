package com.hung.auction.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
public class NotOKStatusMapper implements ExceptionMapper<NotOKStatusException> {

	private static Logger log = Logger.getLogger(NotOKStatusMapper.class);

	public Response toResponse(NotOKStatusException ex) {
		log.info("toResponse: enter");
		Response response = Response.status(ex.getStatusCode()).build();
		log.info("toResponse: exit, response="+response);
		return response;
	}
}
