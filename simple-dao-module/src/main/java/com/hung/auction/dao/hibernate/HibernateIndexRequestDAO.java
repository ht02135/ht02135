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

    public AbstractIndexRequest findById(final Integer id, final String status) {
        log.info("findById: id="+id+", status="+status);

        AbstractIndexRequest request = (AbstractIndexRequest) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from AbstractIndexRequest r where r.id = :id and r.status = :status");
                query.setParameter("id", id);
                query.setParameter("status", status);
                return (AbstractIndexRequest) query.uniqueResult();
            }
        });
        return request;
    }

    @Override
    public TermIndexRequest findByTermId(final String termId, final String status) {
        log.info("findByTermId: termId="+termId+", status="+status);

        TermIndexRequest request = (TermIndexRequest) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from TermIndexRequest r where r.term.id = :termId and r.status = :status");
                query.setParameter("termId", termId);
                query.setParameter("status", status);
                return (TermIndexRequest) query.uniqueResult();
            }
        });
        return request;
    }

    @Override
    public DocumentIndexRequest findByDocumentId(final Integer documentId, final String status) {
        log.info("findByDocumentId: documentId="+documentId+", status="+status);

        DocumentIndexRequest request = (DocumentIndexRequest) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from DocumentIndexRequest r where r.document.id = :documentId and r.status = :status");
                query.setParameter("documentId", documentId);
                query.setParameter("status", status);
                return (DocumentIndexRequest) query.uniqueResult();
            }
        });
        return request;
    }

    public List<AbstractIndexRequest> findAll(final String status) {
        log.info("findAll: status="+status);

        List<AbstractIndexRequest> requests = (List<AbstractIndexRequest>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from AbstractIndexRequest r where r.status = :status");
                query.setParameter("status", status);
                return (List<AbstractIndexRequest>) query.list();
            }
        });
        log.info("findAll: status="+status);

        return requests;
    }

    public void deleteById(Integer id) {
        AbstractIndexRequest request = findById(id, AbstractIndexRequest.PROCESSED_STATUS);

        log.info("deleteById: id="+id+", request="+request);
        getHibernateTemplate().delete(request);
    }

    public void softDeleteById(Integer id) {
        AbstractIndexRequest request = findById(id, AbstractIndexRequest.OPEN_STATUS);
        request.setStatus(AbstractIndexRequest.PROCESSED_STATUS);

        log.info("softDeleteById: id="+id+", request="+request);
        save(request);
    }
}