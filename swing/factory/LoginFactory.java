package com.hung.factory;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.hung.adaptor.LoginServiceAdaptor;
import com.hung.dialog.LoginDialog;


public class LoginFactory {

    private static Logger log = Logger.getLogger(LoginFactory.class);

    private static LoginFactory instance = null;

    public static synchronized LoginFactory getInstance() {
        if (instance == null) { instance = new LoginFactory(); }
        return instance;
    }

    private LoginFactory () {}

    public LoginServiceAdaptor getServiceAdaptor() {
        return LoginServiceAdaptor.getInstance();
    }

    public LoginDialog createLoginDialog(JFrame frame) {
        return new LoginDialog(frame);
    }

}