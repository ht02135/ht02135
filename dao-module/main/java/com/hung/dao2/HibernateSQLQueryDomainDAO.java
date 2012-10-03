package com.hung.auction.dao2;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.hung.auction.domain.Domain;

public class HibernateSQLQueryDomainDAO extends AbstractQueryDomainDAO {

    // "fetch" join allows associations or collections of values to be initialized along with their parent objects

    /*
    select
        d.*
    from
        DOMAIN d
    inner join
        DOMAIN_USER u
            on d.DOMAIN_NAME = u.USER_DOMAIN_NAME
    where
        u.USER_NAME = ?
     */
    // inner join
    public List<Domain> findDomainsByUserName(final String userName, final boolean enableUsersFetch) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                // when you use join in SQL, is auto fetched already?
                String queryString = "select d.* from DOMAIN d inner join DOMAIN_USER u on d.DOMAIN_NAME = u.USER_DOMAIN_NAME where u.USER_NAME = :userName";

                Query query = getSession().createSQLQuery(queryString).addEntity(Domain.class);
                query.setParameter("userName", userName);
                return (List<Domain>) query.list();
            }
        });
    }

    /*
    select
        d.*
    from
        DOMAIN d
    where
        d.DOMAIN_NAME in (
            select
                distinct u.USER_DOMAIN_NAME
            from
                DOMAIN_USER u
            where
                u.USER_NAME = ?
        )
     */
    // sub-query instead of inner-join
    public List<Domain> findDomainsByUserName(final String userName) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                String queryString = "select d.* from DOMAIN d where d.DOMAIN_NAME in (select distinct u.USER_DOMAIN_NAME from DOMAIN_USER u where u.USER_NAME = :userName)";

                Query query = getSession().createSQLQuery(queryString).addEntity(Domain.class);
                query.setParameter("userName", userName);
                return (List<Domain>) query.list();
            }
        });
    }

    /*
    select
        distinct d.*
    from
        DOMAIN d
    left join
        DOMAIN_USER u
            on d.DOMAIN_NAME = u.USER_DOMAIN_NAME
     */
    // left outer join
    public List<Domain> findDomains(final boolean enableUsersFetch) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                // when you use join in SQL, is auto fetched already?
                String queryString = "select distinct d.* from DOMAIN d left join DOMAIN_USER u on d.DOMAIN_NAME = u.USER_DOMAIN_NAME";

                Query query = getSession().createSQLQuery(queryString).addEntity(Domain.class);
                return (List<Domain>) query.list();
            }
        });
    }

    /*
    select
        u.USER_DOMAIN_NAME,
        COUNT(u.USER_DOMAIN_NAME)
    from
        DOMAIN_USER u
    group by
        u.USER_DOMAIN_NAME
    order by
        u.USER_DOMAIN_NAME asc
     */
    // GROUP BY statement is used in conjunction with the aggregate functions to group the result-set
    // return scalar + use group by + aggregation function COUNT
    public List<Object[]> findDomainNameUserCount() {
        return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                String queryString = "select u.USER_DOMAIN_NAME, COUNT(u.USER_DOMAIN_NAME) from DOMAIN_USER u group by u.USER_DOMAIN_NAME order by u.USER_DOMAIN_NAME asc";

                Query query = getSession().createSQLQuery(queryString);
                return (List<Object[]>) query.list();
            }
        });
    }

    /*
    select
        u.USER_DOMAIN_NAME,
        u.USER_NAME
    from
        DOMAIN d full
    join
        DOMAIN_USER u
            on d.DOMAIN_NAME = u.USER_DOMAIN_NAME
    order by
        u.USER_DOMAIN_NAME asc
     */
    // right outer join + full join join
    public List<Object[]> findDomainUserNamePair(final boolean enableNull) {
        return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                String queryString = "select u.USER_DOMAIN_NAME, u.USER_NAME from DOMAIN d right join DOMAIN_USER u on d.DOMAIN_NAME = u.USER_DOMAIN_NAME order by u.USER_DOMAIN_NAME asc";

                if (enableNull) {   // user full join
                    queryString = "select u.USER_DOMAIN_NAME, u.USER_NAME from DOMAIN d full join DOMAIN_USER u on d.DOMAIN_NAME = u.USER_DOMAIN_NAME order by u.USER_DOMAIN_NAME asc";
                }

                Query query = getSession().createSQLQuery(queryString);
                return (List<Object[]>) query.list();
            }
        });
    }

    /*
    self-join via inner join
    select
        d.*
    from
        DOMAIN d
    inner join
        DOMAIN d2
            on d.PARENT_DOMAIN_NAME = d2.DOMAIN_NAME
    inner join
        DOMAIN_USER u
            on d2.DOMAIN_NAME = u.USER_DOMAIN_NAME
    where
        u.USER_NAME = ?

    just sub-query
    select
        d.*
    from
        DOMAIN d
    where
        d.PARENT_DOMAIN_NAME in (
            select
                u.USER_DOMAIN_NAME
            from
                DOMAIN_USER u
            where
                u.USER_NAME = ?
        )
     */
    // self-join via inner join or just plain sub-query
    public List<Domain> findDomainsWithParentContainUserName(final String userName, final boolean enableSubQuery) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                String queryString = "select d.* from DOMAIN d " +
                                     "inner join DOMAIN d2 on d.PARENT_DOMAIN_NAME = d2.DOMAIN_NAME " +
                                     "inner join DOMAIN_USER u on d2.DOMAIN_NAME = u.USER_DOMAIN_NAME " +
                                     "where u.USER_NAME = :userName";

                if (enableSubQuery) {
                    queryString = "select d.* from DOMAIN d " +
                                  "where d.PARENT_DOMAIN_NAME in (select u.USER_DOMAIN_NAME from DOMAIN_USER u where u.USER_NAME = :userName)";
                }

                Query query = getSession().createSQLQuery(queryString).addEntity(Domain.class);
                query.setParameter("userName", userName);
                return (List<Domain>) query.list();
            }
        });
    }
}
