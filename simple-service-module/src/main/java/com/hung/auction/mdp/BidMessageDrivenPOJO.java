package com.hung.auction.mdp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.hung.auction.dao.BidDAO;
import com.hung.auction.domain.Bid;
import com.hung.auction.service.EmailService;

public class BidMessageDrivenPOJO implements MessageListener {
	
	private static Logger log = Logger.getLogger(BidMessageDrivenPOJO.class);
	
	private EmailService emailService;
	private BidDAO bidDAO;
	
    public void onMessage(Message message) {
    	log.info("onMessage: enter...");
    	
    	try {
    		if (message instanceof ObjectMessage) {
    			log.info("onMessage: message is ObjectMessage...");
    			
            	Bid bid = (Bid) ((ObjectMessage) message).getObject();
            	processBid(bid);
    		}
    		else if (message instanceof TextMessage) {
    			log.info("onMessage: message is TextMessage...");
    			
    			TextMessage txtMsg = (TextMessage) message;
    			log.info("onMessage: text="+txtMsg.getText());
    			Integer bidId = Integer.valueOf(txtMsg.getText());
    			Bid bid = bidDAO.findById(bidId);
    			processBid(bid);
    		}
    		else {
                throw new IllegalArgumentException("onMessage: wrong message type");
            }
    	}
    	catch (JMSException ex) {
            // throw new RuntimeException(ex);
    		log.error("onMessage: ex="+ex);
        }
    	
    	log.info("onMessage: exit...");
    }

	public void processBid(Bid bid) {
		log.info("notify serller about new bid "+bid.toString()+" via EmailService");
		emailService.notifySeller(bid);
	}
	
	// injection methods

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void setBidDAO(BidDAO bidDAO) {
		this.bidDAO = bidDAO;
	}
}
