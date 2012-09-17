package com.hung.auction.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.dao.TermDAO;
import com.hung.auction.dao.strategy.SaveOrUpdatePersistenceStrategy;
import com.hung.auction.domain.Term;

@Repository("termDAO")
public class HibernateTermDAO extends HibernateDaoSupport implements TermDAO {

	private static Logger log = Logger.getLogger(HibernateTermDAO.class);
	
    @Autowired
    public HibernateTermDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
    	super();
        setSessionFactory(sessionFactory);
    }
    
    public void save(Term term) {
    	log.info("save: term="+term);
    	
    	SaveOrUpdatePersistenceStrategy.getInstance().persist(getHibernateTemplate(), term);
    }
    
    public Term findById(final String id) {
    	log.info("findById: id="+id);
    	
    	Term term = (Term) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from Term t where t.id = :id");
				query.setParameter("id", id);
				return (Term) query.uniqueResult();
			}
		});
    	log.info("findById: term="+term);
    	
    	return term;
    }
    
    public List<Term> findAll() {
    	return (List<Term>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery("from Term t");
				query.setCacheable(true);	// enable query cache
				return (List<Term>) query.list();
			}
		});
    }
}