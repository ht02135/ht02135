package com.hung.auction.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.dao.DomainDAO;
import com.hung.auction.dao.strategy.MergePersistenceStrategy;
import com.hung.auction.domain.Domain;
import com.hung.auction.domain.DomainUser;

@Repository("domainDAO")
public class HibernateDomainDAO extends HibernateDaoSupport implements DomainDAO {

    private static Logger log = Logger.getLogger(HibernateDomainDAO.class);

    public HibernateDomainDAO() { super(); }

    // in order to use @Repository to create a repository, it needs sessionFactory
    // setSessionFactory() is final, so you can't override it to add an @Autowired annotation
    // you can apply @Autowired to the arbitrary method and call setSessionFactory()
    @Autowired
    public HibernateDomainDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
    }

    public void save(Domain domain) {
        // merge is probably better option than reattachment via save, update, saveOrUpdate world of hurt operations
        MergePersistenceStrategy.getInstance().persist(getHibernateTemplate(), domain);
    }

    public Domain findByName(final String name) {
        return (Domain) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from Domain d where d.name = :name");
                query.setParameter("name", name);
                return (Domain) query.uniqueResult();
            }
        });
    }

    public List<String> getNamesByPatternStoreProcedure(final String pattern) {
        return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getNamesByPatternStoreProcedure");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<String>) query.list();
            }
        });
    }

    public List<String> getNamesByPatternSQL(final String pattern) {
        return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getNamesByPatternSQL");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<String>) query.list();
            }
        });
    }

    public List<String> getParentNamesByPatternSQL(final String pattern) {
        return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getParentNamesByPatternSQL");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<String>) query.list();
            }
        });
    }

    public List<Domain> getDomainsByPatternStoreProcedure(final String pattern) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getDomainsByPatternStoreProcedure");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<Domain>) query.list();
            }
        });
    }

    public List<Domain> getDomainsByPatternSQL(final String pattern) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getDomainsByPatternSQL");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<Domain>) query.list();
            }
        });
    }

    public List<Domain> getDomainsByFieldsPatternSQL(final String pattern) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getDomainsByFieldsPatternSQL");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<Domain>) query.list();
            }
        });
    }

    public List<Domain> getDomainsByAliasFieldsPatternSQL(final String pattern) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getDomainsByAliasFieldsPatternSQL");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<Domain>) query.list();
            }
        });
    }

    public List<Domain> getDomainsByPatternHQL(final String pattern) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getDomainsByPatternHQL");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<Domain>) query.list();
            }
        });
    }

    public List<Domain> findAll() {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from Domain d");
                return (List<Domain>) query.list();
            }
        });
    }

    // -----------------------------------------------------------------------------------------------
    // Mixed scalar-type & entity-type result

    public List<Object[]> getMixedResult(final String pattern) {
        return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().getNamedQuery("getMixedResult");
                query.setParameter("pattern", '%'+pattern+'%');
                return (List<Object[]>) query.list();
            }
        });
    }

    public List<Domain> findByUserNameHQL(final String userName) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from Domain d inner join d.users u where u.name = :userName");
                query.setParameter("userName", userName);
                return (List<Domain>) query.list();
            }
        });
    }

    public List<Domain> findByUserNameHQLFetch(final String userName) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                // join fetch operation to eagerly fill a collection
                // this query returns all domain with users collection (eagerly loads the users collection for each domain)
                Query query = getSession().createQuery("from Domain d inner join fetch d.users u where u.name = :userName");
                query.setParameter("userName", userName);
                return (List<Domain>) query.list();
            }
        });
    }

    // -----------------------------------------------------------------------------------------------
    // Criteria

    public List<Domain> findByUserNameCriteria(final String userName) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria domainCriteria = getSession().createCriteria(Domain.class)
                    .createCriteria("users")	// join association for restriction via Criteria
                    .add(Restrictions.eq("name", userName));
                return (List<Domain>) domainCriteria.list();
            }
        });
    }

    public List<Domain> findByUserNameCriteriaFetch(final String userName) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                // join fetch operation to eagerly fill a collection
                // this query returns all domain with users collection (eagerly loads the users collection for each domain)
                Criteria domainCriteria = getSession().createCriteria(Domain.class)
                    .createAlias("users", "u", CriteriaSpecification.INNER_JOIN)	// join association for restriction via Criteria
                    .setFetchMode("u", FetchMode.JOIN)
                    .add(Restrictions.eq("u.name", userName));
                return (List<Domain>) domainCriteria.list();
            }
        });
    }

    // -----------------------------------------------------------------------------
    // SubQuery

    public List<Domain> findByUserNameSubQueryHQL(final String userName) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Query query = getSession().createQuery("from Domain d where d.name in (select u.userDomain.name from DomainUser u where u.name = :userName)");
                query.setParameter("userName", userName);
                return (List<Domain>) query.list();
            }
        });
    }

    public List<Domain> findByUserNameSubQueryCriteria(final String userName) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                // subQuery
                DetachedCriteria subQuery = DetachedCriteria.forClass( DomainUser.class, "u" );
                subQuery.add(Restrictions.eq("u.name", userName))
                        .setProjection(Property.forName( "u.userDomain.name" ));

                // Query
                Criteria domainCriteria = getSession().createCriteria(Domain.class, "d");
                domainCriteria.add(Property.forName("d.name").in(subQuery));

                return (List<Domain>) domainCriteria.list();
            }
        });
    }

    public void deleteByName(String name) {
        Domain domain = findByName(name);
        getHibernateTemplate().delete(domain);
    }
}