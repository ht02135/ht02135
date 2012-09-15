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

import com.hung.auction.dao.TermDocumentIndexDAO;
import com.hung.auction.dao.strategy.SaveOrUpdatePersistenceStrategy;
import com.hung.auction.domain.TermDocumentIndex;

@Repository("termDocumentIndexDAO")
public class HibernateTermDocumentIndexDAO extends HibernateDaoSupport implements TermDocumentIndexDAO {

	private static Logger log = Logger.getLogger(HibernateTermDocumentIndexDAO.class);
	
    @Autowired
    public HibernateTermDocumentIndexDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
    	super();
        setSessionFactory(sessionFactory);
    }
    
    public void save(TermDocumentIndex termDocumentIndex) {
    	log.info("save: termDocumentIndex="+termDocumentIndex);
    	
    	SaveOrUpdatePersistenceStrategy.getInstance().persist(getHibernateTemplate(), termDocumentIndex);
    }
    
    public TermDocumentIndex findById(final Integer id) {
    	log.info("findById: id="+id);
    	
    	TermDocumentIndex index = (TermDocumentIndex) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from TermDocumentIndex i where i.id = :id");
				query.setParameter("id", id);
				return (TermDocumentIndex) query.uniqueResult();
			}
		});
    	log.info("findById: index="+index);
    	
    	return index;
    }

	@Override
	public List<TermDocumentIndex> findByTermId(final String termId) {
    	log.info("findByTermId: termId="+termId);
    	
    	List<TermDocumentIndex> indexes = (List<TermDocumentIndex>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from TermDocumentIndex i where i.term.id = :termId");
				query.setParameter("termId", termId);
				return (List<TermDocumentIndex>) query.list();
			}
		});
    	log.info("findByTermId: indexes="+indexes);
    	
    	return indexes;
	}

	@Override
	public List<TermDocumentIndex> findByDocumentId(final Integer documentId) {
    	log.info("findByDocumentId: documentId="+documentId);
    	
    	List<TermDocumentIndex> indexes = (List<TermDocumentIndex>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from TermDocumentIndex i where i.document.id = :documentId");
				query.setParameter("documentId", documentId);
				return (List<TermDocumentIndex>) query.list();
			}
		});
    	log.info("findByDocumentId: indexes="+indexes);
    	
    	return indexes;
	}
	
    public void deleteById(Integer id) {
    	TermDocumentIndex index = findById(id);
    	getHibernateTemplate().delete(index);
    }
}