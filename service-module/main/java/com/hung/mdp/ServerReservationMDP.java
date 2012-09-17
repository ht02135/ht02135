package com.hung.auction.mdp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import com.hung.auction.client.ClientReservationMessage;
import com.hung.auction.service.ServerReservationService;

public class ServerReservationMDP implements MessageListener {
	
	private static Logger log = Logger.getLogger(ServerReservationMDP.class);
	
	private ServerReservationService serverReservationService;
	
    public void onMessage(Message message) {
    	log.info("onMessage: enter...");
    	
    	try {
    		if (message instanceof ObjectMessage) {
    			ClientReservationMessage clientReservationMessage = (ClientReservationMessage) ((ObjectMessage) message).getObject();
    			log.info("onMessage: clientReservationMessage="+clientReservationMessage);
    			serverReservationService.reserveUnReserve(clientReservationMessage.getReservations(), clientReservationMessage.getUnReservations());
    		}
    		else {
    			log.info("onMessage: wrong message type");
            }
    	}
    	catch (JMSException ex) {
    		log.info("onMessage: ex="+ex);
        }
    	
    	log.info("onMessage: exit...");
    }
    
	// injection methods

	public void setServerReservationService(ServerReservationService serverReservationService) {
		this.serverReservationService = serverReservationService;
	}
}
