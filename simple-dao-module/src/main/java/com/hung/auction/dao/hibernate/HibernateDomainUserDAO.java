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

	// Hibernate reality: it is just ORM layer, not providing much of connection and transaction management
	// Spring reality: it provides connection and transaction management
	// for connection management: look at org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean
	// <prop key="hibernate.connection.pool_size">0</prop>
	// for transaction management: look at org.springframework.orm.hibernate3.HibernateTransactionManager
	// <tx:annotation-driven transaction-manager="transactionManager"/> to work with @Transactional()
	// or aop
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
    
    // Session: it is used t mediate connection with database
    // 1>the session opens a single db connection when it is created and holds onto it until the session is closed.  every
    //   object loaded by hibernate from databae is associated with session.  it allows hibernate to automatically persist object
    //   that are modified and to implement lazy-loading functionality
    // Disconnected Objects
    // 1>if object is evicted from its session
    // 2>or session is closed while the object is still alive.  
    // 3>disconnected object will contiue to work as long as you dont perform any operation that it needs to go back to databse
    // 4>loading object in one session and save to another session will result in error
    public List<DomainUser> findAll() {
    	return (List<DomainUser>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery("from DomainUser du");
				query.setCacheable(true);	// enable query cache
				return (List<DomainUser>) query.list();
			}
		});
    }
    
    public void deleteByLoginId(String loginId) {
        DomainUser domainUser = findByLoginId(loginId);
        getHibernateTemplate().delete(domainUser);
    }
}