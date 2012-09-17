package com.hung.main;

import org.apache.log4j.Logger;

import com.hung.factory.GUIFactoryMethods;

public class DomainUserMain extends AbstractMain {

	private static Logger log = Logger.getLogger(DomainUserMain.class);
    
	public void loggedIn() {
		GUIFactoryMethods.getDomainUserGUIFactory().createFrame();
	}
	
    public static void main( String[] args )
    {
    	DomainUserMain domainUserMain = new DomainUserMain();
    	domainUserMain.login();
    }
}