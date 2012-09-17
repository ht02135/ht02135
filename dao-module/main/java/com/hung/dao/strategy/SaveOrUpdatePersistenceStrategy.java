package com.hung.auction.dao.strategy;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class SaveOrUpdatePersistenceStrategy implements IPersistenceStrategy {

    private static Logger log = Logger.getLogger(SaveOrUpdatePersistenceStrategy.class);

    private static SaveOrUpdatePersistenceStrategy instance = null;

    public static synchronized SaveOrUpdatePersistenceStrategy getInstance() {
        if (instance == null) { instance = new SaveOrUpdatePersistenceStrategy(); }
        return instance;
    }

    // save(), update(), or saveOrUpdate() is best used within fresh session without 0% risk of session
    // containing already persistence instance with same identifier
    // reason to use saveOrUpdate()
    // 1>it is better to push using saveOrUpdate() with fresh session
    // 1>it is better to push session to be short, because session requires db connection (long session is a big NO NO)
    public Object persist(HibernateTemplate hibernateTemplate, Object object) {
        log.info("persist: object="+object);

        hibernateTemplate.saveOrUpdate(object);	// persist given instance
        // hibernateTemplate.flush();	// flush all pending saves, updates, and delete to database
        return object;
    }
}