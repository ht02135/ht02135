package com.hung.auction.dao.strategy;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class MergePersistenceStrategy implements IPersistenceStrategy {

    private static Logger log = Logger.getLogger(MergePersistenceStrategy.class);

    private static MergePersistenceStrategy instance = null;

    public static synchronized MergePersistenceStrategy getInstance() {
        if (instance == null) { instance = new MergePersistenceStrategy(); }
        return instance;
    }

    // update() (YES re-attach detached object): use it if session doesnt contain already persistent instance with same identifier.
    // so use it to re-attach your detached instance in fresh session
    // ------------------------------------------------------------------------------------------------------------------------------------
    // merge() (NO re-attach detached object): use it to merge your modification at any time regardless session contains/not-contains
    // already persistence instance with same identifier and return merged-already persistence instance
    // 1>if there is already persistence instance, copy state of detached object to persistence instance and return that persistences instance
    // 2>if not already persistence instance, load it from db, copy the state, and return newly loaded persistence.
    public Object persist(HibernateTemplate hibernateTemplate, Object object) {
        log.info("persist: object="+object);

        Object retunObject = hibernateTemplate.merge(object);	// persist given instance
        // hibernateTemplate.flush();	// flush all pending saves, updates, and delete to database
        return retunObject;
    }
}