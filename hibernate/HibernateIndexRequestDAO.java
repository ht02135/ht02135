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

import com.hung.auction.dao.IndexRequestDAO;
import com.hung.auction.dao.strategy.SaveOrUpdatePersistenceStrategy;
import com.hung.auction.domain.AbstractIndexRequest;
import com.hung.auction.domain.DocumentIndexRequest;
import com.hung.auction.domain.TermIndexRequest;

@Repository("indexRequestDAO")
public class HibernateIndexRequestDAO extends HibernateDaoSupport implements IndexRequestDAO {

    private static Logger log = Logger.getLogger(HibernateIndexRequestDAO.class);

    @Autowired
    public HibernateIndexRequestDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
    }

    public void save(AbstractIndexRequest indexRequest) {
        log.info("save: indexRequest="+indexRequest);

        SaveOrUpdatePersistenceStrategy.getInstance().persist(getHibernateTemplate(), indexRequest);
    }

    public AbstractIndexRequest findById(final Integer id) {
        log.info("findById: id="+id);

        AbstractIndexRequest request = (AbstractIndexRequest) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from AbstractIndexRequest r where r.id = :id");
                query.setParameter("id", id);
                return (AbstractIndexRequest) query.uniqueResult();
            }
        });
        log.info("findById: request="+request);

        return request;
    }

    @Override
    public TermIndexRequest findByTermId(final String termId) {
        log.info("findByTermId: termId="+termId);

        TermIndexRequest request = (TermIndexRequest) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from TermIndexRequest r where r.term.id = :termId");
                query.setParameter("termId", termId);
                return (TermIndexRequest) query.uniqueResult();
            }
        });
        log.info("findByTermId: request="+request);

        return request;
    }

    @Override
    public DocumentIndexRequest findByDocumentId(final Integer documentId) {
        log.info("findByDocumentId: documentId="+documentId);

        DocumentIndexRequest request = (DocumentIndexRequest) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from DocumentIndexRequest r where r.document.id = :documentId");
                query.setParameter("documentId", documentId);
                return (DocumentIndexRequest) query.uniqueResult();
            }
        });
        log.info("findByDocumentId: request="+request);

        return request;
    }

    public void deleteById(Integer id) {
        AbstractIndexRequest request = findById(id);
        getHibernateTemplate().delete(request);
    }
}