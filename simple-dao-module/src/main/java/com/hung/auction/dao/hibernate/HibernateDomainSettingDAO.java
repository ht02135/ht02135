package com.hung.auction.dao.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.dao.DomainSettingDAO;
import com.hung.auction.domain.BooleanDomainSetting;
import com.hung.auction.domain.IntegerDomainSetting;
import com.hung.auction.domain.StringDomainSetting;

@Repository("domainSettingDAO")
public class HibernateDomainSettingDAO extends HibernateDaoSupport implements DomainSettingDAO {

	private static Logger log = Logger.getLogger(HibernateDomainSettingDAO.class);
	
    @Autowired
    public HibernateDomainSettingDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
    }
	
    public void save(StringDomainSetting stringDomainSetting) {
    	log.info("save: stringDomainSetting="+stringDomainSetting);
    	if (stringDomainSetting instanceof StringDomainSetting) {
    		if (findStringDomainSetting(stringDomainSetting.getName(), stringDomainSetting.getSettingDomain().getName()) == null)
    			getHibernateTemplate().save(stringDomainSetting);
    	}
    	if (stringDomainSetting instanceof BooleanDomainSetting) {
    		if (findBooleanDomainSetting(stringDomainSetting.getName(), stringDomainSetting.getSettingDomain().getName()) == null)
    			getHibernateTemplate().save((BooleanDomainSetting) stringDomainSetting);
    	}
    	if (stringDomainSetting instanceof IntegerDomainSetting) {
    		if (findIntegerDomainSetting(stringDomainSetting.getName(), stringDomainSetting.getSettingDomain().getName()) == null)
    			getHibernateTemplate().save((IntegerDomainSetting) stringDomainSetting);
    	}
    }
    
    public StringDomainSetting findStringDomainSetting(final String settingName, final String domainName) {
    	StringDomainSetting stringDomainSetting = (StringDomainSetting) findStringDomainSetting("StringDomainSetting", settingName, domainName);
    	if (stringDomainSetting != null) log.info("findStringDomainSetting: stringDomainSetting="+stringDomainSetting);
    	return stringDomainSetting;
    }
    
    public BooleanDomainSetting findBooleanDomainSetting(final String settingName, final String domainName) {
    	BooleanDomainSetting booleanDomainSetting = (BooleanDomainSetting) findStringDomainSetting("BooleanDomainSetting", settingName, domainName);
    	if (booleanDomainSetting != null) log.info("findBooleanDomainSetting: booleanDomainSetting="+booleanDomainSetting);
    	return booleanDomainSetting;
    }
    
    public IntegerDomainSetting findIntegerDomainSetting(String settingName, String domainName) {
    	IntegerDomainSetting integerDomainSetting = (IntegerDomainSetting) findStringDomainSetting("IntegerDomainSetting", settingName, domainName);
    	if (integerDomainSetting != null) log.info("findIntegerDomainSetting: integerDomainSetting="+integerDomainSetting);
    	return integerDomainSetting;
    }
    
    private StringDomainSetting findStringDomainSetting(String className, final String settingName, final String domainName) {
    	final String queryString = "from "+className+" s where s.name = :settingName and s.settingDomain.name = :domainName";
    	
    	StringDomainSetting stringDomainSetting = null;
    	try {
    		stringDomainSetting = (StringDomainSetting) getHibernateTemplate().execute(new HibernateCallback() {
    			public Object doInHibernate(Session session) {
    				Query query = getSession().createQuery(queryString);
    				query.setParameter("settingName", settingName);
    				query.setParameter("domainName", domainName);
    				return (StringDomainSetting) query.uniqueResult();
    			}
    		});
    	} catch (Exception e) {
    		log.info("findStringDomainSetting: e="+e);
    	}
    	
    	return stringDomainSetting;
    }
}