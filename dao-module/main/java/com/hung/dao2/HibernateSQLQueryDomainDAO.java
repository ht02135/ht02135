package com.hung.auction.dao2;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.domain.Domain;

@Repository("hQLQueryDomainDAO")
public class HibernateSQLQueryDomainDAO extends HibernateDaoSupport implements QueryDomainDAO {

    // "fetch" join allows associations or collections of values to be initialized along with their parent objects

    /*
    select
        d.*
    from
        DOMAIN d
    inner join
        DOMAIN_USER u
            on d.DOMAIN_NAME = u.USER_DOMAIN_NAME
            and u.USER_NAME = ?
     */
    // inner join
    public List<Domain> findDomainsByUserName(final String userName, final boolean enableUsersFetch) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                // when you use join in SQL, is auto fetched already?
                String queryString = "select d.* from DOMAIN d inner join DOMAIN_USER u on d.DOMAIN_NAME = u.USER_DOMAIN_NAME and u.USER_NAME = :userName";

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
    inner join
        DOMAIN_USER u
            on d.DOMAIN_NAME = u.USER_DOMAIN_NAME
     */
    // left outer join
    public List<Domain> findDomains(final boolean enableUsersFetch) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                // when you use join in SQL, is auto fetched already?
                String queryString = "select d.* from DOMAIN d inner join DOMAIN_USER u on d.DOMAIN_NAME = u.USER_DOMAIN_NAME";

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
    public List<Object[]> findDomainNameUsersCount() {
        return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                String queryString = "select u.USER_DOMAIN_NAME, COUNT(u.USER_DOMAIN_NAME) from DOMAIN_USER u group by u.USER_DOMAIN_NAME order by u.USER_DOMAIN_NAME asc";

                Query query = getSession().createSQLQuery(queryString);
                return (List<Object[]>) query.list();
            }
        });
    }

    // right outer join

    // full join
}