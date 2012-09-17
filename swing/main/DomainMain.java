package com.hung.main;

import org.apache.log4j.Logger;

import com.hung.factory.GUIFactoryMethods;

public class DomainMain extends AbstractMain {

	private static Logger log = Logger.getLogger(DomainMain.class);
    
	public void loggedIn() {
		GUIFactoryMethods.getDomainGUIFactory().createFrame();
	}
	
    public static void main( String[] args )
    {
    	DomainMain domainMain = new DomainMain();
    	domainMain.login();
    }
}