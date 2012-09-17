package com.hung.main;

import javax.swing.JFrame;

import com.hung.adaptor.ILoginObserver;
import com.hung.dialog.LoginDialog;
import com.hung.factory.FactoryMethods;

public abstract class AbstractMain implements ILoginObserver {

	public AbstractMain() {
		FactoryMethods.getLoginFactory().getServiceAdaptor().addLoginObserver(this);
	}

	public void login() {
		// new a throw away JFrame required by JDialog
		LoginDialog loginDialog = FactoryMethods.getLoginFactory().createLoginDialog(new JFrame());
    	loginDialog.setVisible(true);
	}
}