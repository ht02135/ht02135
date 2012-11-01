package com.hung.auction.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/* httpSession
   1>servlet container manage http session via cookies or url rewrite like
     http://127.0.0.1:8081/simple-restfulwebapp-module/auction/domains/root;jsessionid=13y0cxjhvy0cj
   2>when i access the page, before login, sessionCreated(..) is called...
   3>may be i can use this fact with Spring security and login mechanism (LoginController, LoginService, Login-blabla)
*/
public class SessionCounterListener implements HttpSessionListener {

	private static Logger log = Logger.getLogger(SessionCounterListener.class);
	
	private static int totalSessionCount;
	
	@Override
	public synchronized void sessionCreated(HttpSessionEvent evt) {
		increaseSessionCount();
		log.info(">>>>>>>>>> sessionCreated: totalSessionCount="+totalSessionCount+" <<<<<<<<<<");
	}

	@Override
	public synchronized void sessionDestroyed(HttpSessionEvent evt) {
		decreaseSessionCount();
		log.info(">>>>>>>>>> sessionDestroyed: totalSessionCount="+totalSessionCount+" <<<<<<<<<<");
	}
	
	private synchronized void increaseSessionCount() {
		totalSessionCount++;
	}

	private synchronized void decreaseSessionCount() {
		totalSessionCount--;
	}
}
