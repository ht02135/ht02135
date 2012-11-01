package com.hung.auction.service;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hung.auction.domain.Bid;

public class EmailServiceImpl implements EmailService {
	
	private static Logger log = Logger.getLogger(EmailServiceImpl.class);
	
	private JavaMailSender mailSender;
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void notifySeller(Bid bid) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		String setFrom    = "ht02135@yahoo.com";
		String setTo      = bid.getItem().getSeller().getEmail();
		String setSubject = "a bid from "+bid.getBidder().getName()+" for your item"+bid.getItem().getName();
		String setText    = bid.getBidder().getName()+" placed a bid "+bid.getPrice()+" for your item "+bid.getItem().getName();
				
		log.info("setFrom="+setFrom);
		log.info("setTo="+setTo);
		log.info("setSubject="+setSubject);
		log.info("setText="+setText);
		
		message.setFrom(setFrom);
		message.setTo(setTo);
		message.setSubject(setSubject);
		message.setText(setText);
		
		log.info("sending message...");
		mailSender.send(message);
		log.info("sending message... done");
	}
	
	// injection methods

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
