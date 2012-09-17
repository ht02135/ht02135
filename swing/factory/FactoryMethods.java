package com.hung.factory;

import org.apache.log4j.Logger;


public class FactoryMethods {
	private static Logger log = Logger.getLogger(FactoryMethods.class);

	public static IFactory getDomainFactory() {
		return DomainFactory.getInstance();
	}
	
	public static IFactory getDomainUserFactory() {
		return DomainUserFactory.getInstance();
	}
	
	public static LoginFactory getLoginFactory() {
		return LoginFactory.getInstance();
	}
	
	public static DomainSettingFactory getDomainSettingFactory() {
		return DomainSettingFactory.getInstance();
	}
	
	public static ClientCacheFactory getClientCacheFactory() {
		return ClientCacheFactory.getInstance();
	}
	
	public static ClientReservationFactory getClientReservationFactory() {
		return ClientReservationFactory.getInstance();
	}
	
	public static ServerReservationFactory getServerReservationFactory() {
		return ServerReservationFactory.getInstance();
	}
}