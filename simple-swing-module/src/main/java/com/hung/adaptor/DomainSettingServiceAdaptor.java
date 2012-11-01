package com.hung.adaptor;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.domain.BooleanDomainSetting;
import com.hung.auction.domain.IntegerDomainSetting;
import com.hung.auction.domain.StringDomainSetting;
import com.hung.auction.service.DomainSettingService;
import com.hung.factory.FactoryMethods;

public class DomainSettingServiceAdaptor {

    private static Logger log = Logger.getLogger(DomainSettingServiceAdaptor.class);

    private DomainSettingService domainSettingService = null;

    private static DomainSettingServiceAdaptor instance = null;

    public static synchronized DomainSettingServiceAdaptor getInstance() {
        if (instance == null) { instance = new DomainSettingServiceAdaptor(); }
        return instance;
    }

    private DomainSettingServiceAdaptor() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
        domainSettingService = (DomainSettingService) applicationContext.getBean("httpDomainSettingServiceExporter");
    }

    public StringDomainSetting getStringDomainSetting(String settingName) {
        String domainName = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession().getDomainName();
        return domainSettingService.getStringDomainSetting(settingName, domainName);
    }

    public BooleanDomainSetting getBooleanDomainSetting(String settingName) {
        String domainName = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession().getDomainName();
        return domainSettingService.getBooleanDomainSetting(settingName, domainName);
    }

    public IntegerDomainSetting getIntegerDomainSetting(String settingName) {
        String domainName = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession().getDomainName();
        return domainSettingService.getIntegerDomainSetting(settingName, domainName);
    }
}