package com.hung.main;

import org.apache.log4j.Logger;

import com.hung.frame.DisplayCurrentDomainUserFrame;

public class DisplayCurrentDomainUserMain extends AbstractMain {

	private static Logger log = Logger.getLogger(DisplayCurrentDomainUserMain.class);
    
	public void loggedIn() {
		// dont have factory to do this, just new it for now
		new DisplayCurrentDomainUserFrame();
	}
	
    public static void main( String[] args )
    {
    	DisplayCurrentDomainUserMain displayCurrentDomainUserMain = new DisplayCurrentDomainUserMain();
    	displayCurrentDomainUserMain.login();
    }
}