package com.hung.auction.service;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.dao.BidDAO;
import com.hung.auction.domain.Bid;
import com.hung.auction.domain.Bidder;

public class BidServiceImpl implements BidService {
	
	private static Logger log = Logger.getLogger(BidServiceImpl.class);
	
	private JmsTemplate jmsTemplate;
	private Destination destination;
	private BidDAO bidDAO;
	 
	@Transactional(propagation=Propagation.SUPPORTS)
	public void publish(final Bid bid) {
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(bid);
			}
		});
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void publish(final Integer bidId) {
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(bidId.toString());
			}
		});
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(Bid bid) {
		bidDAO.save(bid);
		publish(bid);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public List<Bid> findByBidder(Bidder bidder) {
		return bidDAO.findByBidder(bidder);
	}
	
	@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
	public Bid findById(Integer id) {
		return bidDAO.findById(id);
	}
	
	// injection methods

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public void setBidDAO(BidDAO bidDAO) {
		this.bidDAO = bidDAO;
	}
}
