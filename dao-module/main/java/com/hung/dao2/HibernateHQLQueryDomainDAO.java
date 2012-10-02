package com.hung.auction.dao2;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.domain.Domain;

@Repository("hQLQueryDomainDAO")
public class HibernateHQLQueryDomainDAO extends HibernateDaoSupport implements QueryDomainDAO {

    // "fetch" join allows associations or collections of values to be initialized along with their parent objects

    /*
    select
        domain0_.DOMAIN_NAME as DOMAIN1_180_0_,
        users1_.USER_LOGIN_ID as USER1_179_1_,
        domain0_.DOMAIN_DESCRIPTION as DOMAIN2_180_0_,
        domain0_.PARENT_DOMAIN_NAME as PARENT3_180_0_,
        users1_.USER_NAME as USER2_179_1_,
        users1_.USER_DOMAIN_NAME as USER3_179_1_,
        users1_.USER_DOMAIN_NAME as USER3_0__,
        users1_.USER_LOGIN_ID as USER1_0__
    from
        DOMAIN domain0_
    inner join
        DOMAIN_USER users1_
            on domain0_.DOMAIN_NAME=users1_.USER_DOMAIN_NAME
    where
        users1_.USER_NAME=?
     */
    // inner join
    public List<Domain> findDomainsByUserName(final String userName, final boolean enableUsersFetch) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                String queryString = "from Domain d inner join d.users u where u.name = :userName";
                if (enableUsersFetch) queryString = "from Domain d inner join fetch d.users u where u.name = :userName";

                Query query = getSession().createQuery(queryString);
                query.setParameter("userName", userName);
                return (List<Domain>) query.list();
            }
        });
    }

    /*
    select
        domain0_.DOMAIN_NAME as DOMAIN1_180_0_,
        users1_.USER_LOGIN_ID as USER1_179_1_,
        domain0_.DOMAIN_DESCRIPTION as DOMAIN2_180_0_,
        domain0_.PARENT_DOMAIN_NAME as PARENT3_180_0_,
        users1_.USER_NAME as USER2_179_1_,
        users1_.USER_DOMAIN_NAME as USER3_179_1_,
        users1_.USER_DOMAIN_NAME as USER3_0__,
        users1_.USER_LOGIN_ID as USER1_0__
    from
        DOMAIN domain0_
    left outer join
        DOMAIN_USER users1_
            on domain0_.DOMAIN_NAME=users1_.USER_DOMAIN_NAME
     */
    // left outer join
    public List<Domain> findDomains(final boolean enableUsersFetch) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                String queryString = "from Domain d ";
                if (enableUsersFetch) { // it really makes sense to do left outer join + fetch
                    queryString += "left join fetch d.users";
                }

                Query query = getSession().createQuery(queryString);
                return (List<Domain>) query.list();
            }
        });
    }

    // return scalar + use group by + aggregation function COUNT
    // return empty list, cuz dont think there is way to do in HQL
    public List<Object[]> findDomainNameUsersCount() {
        return Collections.EMPTY_LIST;
    }

    // ###### in context of HQL (object oriented query) ###############

    // GROUP BY statement is used in conjunction with the aggregate functions to group the result-set
    // usually mean returning scalar value (dont think is possible, do it in SQL)

    // right outer join (full join fetch and right join fetch are not meaningful)

    // full join (full join fetch and right join fetch are not meaningful)
    // ###### in context of HQL (object oriented query) ###############
}