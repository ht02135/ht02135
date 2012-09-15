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

import com.hung.auction.dao.DocumentDAO;
import com.hung.auction.dao.strategy.SaveOrUpdatePersistenceStrategy;
import com.hung.auction.domain.AbstractDocument;
import com.hung.auction.domain.StringDocument;

@Repository("documentDAO")
public class HibernateDocumentDAO extends HibernateDaoSupport implements DocumentDAO {

    private static Logger log = Logger.getLogger(HibernateDocumentDAO.class);

    public HibernateDocumentDAO() {
        super();
    }

    @Autowired
    public HibernateDocumentDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
    }

    public void save(AbstractDocument document) {
        log.info("save: document="+document);

        SaveOrUpdatePersistenceStrategy.getInstance().persist(getHibernateTemplate(), document);
    }

    public AbstractDocument findById(final Integer id) {
        log.info("findById: id="+id);

        AbstractDocument abstractDocument = (AbstractDocument) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from AbstractDocument ad where ad.id = :id");
                query.setParameter("id", id);
                return (AbstractDocument) query.uniqueResult();
            }
        });

        return abstractDocument;
    }

    public List<AbstractDocument> findAll() {
        return (List<AbstractDocument>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from AbstractDocument ad");
                return (List<AbstractDocument>) query.list();
            }
        });
    }

    public List<StringDocument> findStringDocuments() {
        return (List<StringDocument>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from StringDocument sd");
                return (List<StringDocument>) query.list();
            }
        });
    }

    public List<StringDocument> findByTerm(final String term) {
        return findBySearchText(term);
    }

    public List<StringDocument> findBySearchText(final String searchText) {
        log.info("findBySearchText: searchText="+searchText);

        return (List<StringDocument>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from StringDocument sd where sd.strContent like :searchText");
                query.setParameter("searchText", '%'+searchText+'%');
                return (List<StringDocument>) query.list();
            }
        });
    }

    public List<StringDocument> findBySearchTextSQL(final String searchText) {
        log.info("findBySearchTextSQL: searchText="+searchText);

        return (List<StringDocument>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                final String queryString = "select * from DOCUMENT where DOCUMENT_TYPE = 'STRING' and contains(DOCUMENT_STRCONTENT, :searchText, 1) > 0";
                Query query = getSession().createQuery(queryString);
                query.setParameter("searchText", '%'+searchText+'%');
                return (List<StringDocument>) query.list();
            }
        });
    }

    public void deleteById(Integer id) {
        log.info("deleteById: id="+id);

        AbstractDocument document = findById(id);
        getHibernateTemplate().delete(document);
    }
}