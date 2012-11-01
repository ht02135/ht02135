package com.hung.mdp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import com.hung.auction.client.ClientReservationMessage;
import com.hung.factory.FactoryMethods;

public class ClientReservationMDP implements MessageListener {
	
	private static Logger log = Logger.getLogger(ClientReservationMDP.class);
	
    public void onMessage(Message message) {
    	log.info("onMessage: enter...");
    	
    	try {
    		if (message instanceof ObjectMessage) {
    			ClientReservationMessage clientReservationMessage = (ClientReservationMessage) ((ObjectMessage) message).getObject();
    			log.info("onMessage: clientReservationMessage="+clientReservationMessage);
    			FactoryMethods.getClientReservationFactory().getCache().synchUnReservations(clientReservationMessage.getUnReservations());
    			FactoryMethods.getClientReservationFactory().getCache().synchReservations(clientReservationMessage.getReservations());
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
}
