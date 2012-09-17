package com.hung.factory;

import org.apache.log4j.Logger;


public class GUIFactoryMethods {
	private static Logger log = Logger.getLogger(GUIFactoryMethods.class);
	
	public static IGUIFactory getDomainGUIFactory() {
		return DomainFactory.getInstance();
	}
	
	public static IGUIFactory getDomainUserGUIFactory() {
		return DomainUserFactory.getInstance();
	}
}