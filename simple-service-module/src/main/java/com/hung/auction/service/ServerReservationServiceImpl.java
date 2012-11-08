package com.hung.auction.service;

import java.util.Collections;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.hung.auction.cache.ServerReservationCache;
import com.hung.auction.client.ClientReservation;
import com.hung.auction.client.ClientReservationMessage;

public class ServerReservationServiceImpl implements ServerReservationService {

	private static Logger log = Logger.getLogger(ServerReservationServiceImpl.class);
	
	private JmsTemplate jmsTemplate;
	private ActiveMQTopic destination;
	
	public ServerReservationServiceImpl() {}

	public void reserveUnReserve(List<ClientReservation> clientReservations, List<ClientReservation> clientUnReservations) {
		// always handle unreserve and then reserve
		log.info("reserveUnReserve: clientReservations="+clientReservations+", clientUnReservations="+clientUnReservations);
		
		List<ClientReservation> serverUnReservations = Collections.EMPTY_LIST;
		List<ClientReservation> serverReservations = Collections.EMPTY_LIST;
		if (clientUnReservations.size() > 0) {
			serverUnReservations = ServerReservationCache.getInstance().synchUnReservations(clientUnReservations);
		}
		if (clientReservations.size() > 0) {
			serverReservations = ServerReservationCache.getInstance().synchReservations(clientReservations);
		}
		if ((serverReservations.size() > 0) || (serverUnReservations.size() > 0)) {
			publishReserveUnReserve(serverReservations, serverUnReservations);
		}
	}
	
	private void publishReserveUnReserve(List<ClientReservation> serverReservations, List<ClientReservation> serverUnReservations) {
		try {
			final ClientReservationMessage clientReservationMessage = new ClientReservationMessage(serverReservations, serverUnReservations);
			log.info("publishReserveUnReserve: publish clientReservationMessage="+clientReservationMessage+" to destination.getTopicName()="+destination.getTopicName());
			jmsTemplate.send(destination, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage(clientReservationMessage);
				}
			});
		} catch (JMSException e) {
			log.error("publishReserveUnReserve: e="+e);
		}
	}
	
	// injection methods

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void setDestination(ActiveMQTopic destination) {
		this.destination = destination;
	}
}
