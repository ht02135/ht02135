package com.hung.adaptor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.client.ClientSession;
import com.hung.auction.service.LoginService;

public class LoginServiceAdaptor {

    private static Logger log = Logger.getLogger(LoginServiceAdaptor.class);

    private LoginService loginService = null;
    private ClientSession clientSession = null;
    private List<ILoginObserver> observers = new ArrayList();

    private static LoginServiceAdaptor instance = null;

    public static synchronized LoginServiceAdaptor getInstance() {
        if (instance == null) { instance = new LoginServiceAdaptor(); }
        return instance;
    }

    private LoginServiceAdaptor() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
        loginService = (LoginService) applicationContext.getBean("httpLoginServiceExporter");
    }

    public boolean login(String loginId, String domainName) {
        boolean loggedin =  loginService.login(loginId, domainName).booleanValue();
        if (loggedin) {
            clientSession = new ClientSession(domainName, loginId);
            log.info("login: clientSession="+clientSession);
            fireLoggedIn();
        }
        return loggedin;
    }

    public boolean isLoggedIn() {
        return (clientSession != null) ? clientSession.equals(loginService.getClientSession()) : false;
    }

    public boolean logout() {
        boolean isLoggedOut =  loginService.logout().booleanValue();
        if (isLoggedOut) {
            clientSession = null;
            log.info("logout: clientSession="+clientSession);
        } else {
            log.info("logout: logout failed in service side...");
        }
        return isLoggedOut;
    }

    public ClientSession getClientSession() {
        return clientSession;
    }

    public void addLoginObserver(ILoginObserver observer) {
        observers.add(observer);
    }

    public void fireLoggedIn() {
        for (int i=0; i<observers.size(); i++) {
            observers.get(i).loggedIn();
        }
    }
}