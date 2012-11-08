package com.hung.adaptor;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.hung.auction.client.ClientReservation;
import com.hung.auction.client.ClientReservationMessage;
import com.hung.auction.service.ServerReservationService;

public class ServerReservationServiceAdaptor {

    private static Logger log = Logger.getLogger(ServerReservationServiceAdaptor.class);

    private ServerReservationService serverReservationService = null;
    private JmsTemplate jmsTemplate = null;
    private ActiveMQTopic destination = null;

    private static ServerReservationServiceAdaptor instance = null;

    public static synchronized ServerReservationServiceAdaptor getInstance() {
        if (instance == null) { instance = new ServerReservationServiceAdaptor(); }
        return instance;
    }

    private ServerReservationServiceAdaptor() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
        serverReservationService = (ServerReservationService) applicationContext.getBean("httpServerReservationServiceExporter");
        jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
        destination = (ActiveMQTopic) applicationContext.getBean("clientReservationTopic");
    }

    public void reserveUnReserve(List<ClientReservation> clientReservations, List<ClientReservation> clientUnReservations) {
        log.info("reserveUnReserveJMS: send clientReservations="+clientReservations+", clientUnReservations="+clientUnReservations+" to server");
        serverReservationService.reserveUnReserve(clientReservations, clientUnReservations);
    }

    public void publishReserveUnReserve(List<ClientReservation> clientReservations, List<ClientReservation> clientUnReservations) {
        try {
            final ClientReservationMessage clientReservationMessage = new ClientReservationMessage(clientReservations, clientUnReservations);
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
}