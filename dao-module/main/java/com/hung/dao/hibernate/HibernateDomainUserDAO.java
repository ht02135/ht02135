package com.hung.auction.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.dao.DomainUserDAO;
import com.hung.auction.dao.strategy.MergePersistenceStrategy;
import com.hung.auction.dao.strategy.SaveOrUpdatePersistenceStrategy;
import com.hung.auction.domain.DomainUser;

@Repository("domainUserDAO")
public class HibernateDomainUserDAO extends HibernateDaoSupport implements DomainUserDAO {

	private static Logger log = Logger.getLogger(HibernateDomainUserDAO.class);
	
	public HibernateDomainUserDAO() {
		super();
	}
	
    // in order to use @Repository to create a repository, it needs sessionFactory
    // setSessionFactory() is final, so you can't override it to add an @Autowired annotation
    // you can apply @Autowired to the arbitrary method and call setSessionFactory()
    @Autowired
    public HibernateDomainUserDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
    	super();
        setSessionFactory(sessionFactory);
        log.info(">>>>>>>>>> constructor: called to set sessionFactory="+sessionFactory+" <<<<<<<<<<");
    }

    public void save(final DomainUser domainUser) {
    	log.info("save: domainUser="+domainUser);
    	
    	// use getHibernateTemplate().contains() to check if domainUser is attached to this session
    	if (!getHibernateTemplate().contains(domainUser)) {
    		log.info("save: domainUser="+domainUser+" is detached from current session="+getHibernateTemplate().getSessionFactory().getCurrentSession()+", use SaveOrUpdatePersistenceStrategy");
    		SaveOrUpdatePersistenceStrategy.getInstance().persist(getHibernateTemplate(), domainUser);
    		if (getHibernateTemplate().contains(domainUser)) {
    			log.info("save: domainUser="+domainUser+" is re-attached to session="+getHibernateTemplate().getSessionFactory().getCurrentSession()+" now");
    		}
    	} else {
    		log.info("save: session contain domainUser="+domainUser+", use MergePersistenceStrategy");
    		MergePersistenceStrategy.getInstance().persist(getHibernateTemplate(), domainUser);
    	}
    }
    
	// hibernate 1st level cache: it is session cache.  objects are cached within current session until session is closed
	// query cache: it requires 2nd level cache.  it cache identifiers for individual query (it doesnt cache state of actual entities
	// in result set.  it only cache identifier values and results of value type.
    public DomainUser findByLoginId(final String loginId) {
    	return (DomainUser) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery("from DomainUser du where du.loginId = :loginId");
				// setCacheable(true) to use query cache
				query.setCacheable(true);	// enable query cache
				// FlushMode.AUTO (default) : Session is sometimes flushed before query execution to ensure that queries never return stale state
				query.setFlushMode(FlushMode.AUTO);
				query.setParameter("loginId", loginId);
				return (DomainUser) query.uniqueResult();
			}
		});
    }
    
    public List<DomainUser> findAll() {
    	return (List<DomainUser>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery("from DomainUser du");
				query.setCacheable(true);	// enable query cache
				return (List<DomainUser>) query.list();
			}
		});
    }
}