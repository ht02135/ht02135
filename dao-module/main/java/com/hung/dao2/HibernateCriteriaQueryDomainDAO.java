package com.hung.auction.dao2;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.domain.Domain;
import com.hung.auction.domain.DomainUser;

@Repository("hQLQueryDomainDAO")
public class HibernateCriteriaQueryDomainDAO extends HibernateDaoSupport implements QueryDomainDAO {

    // "fetch" join allows associations or collections of values to be initialized along with their parent objects

    /*
    select
        this_.DOMAIN_NAME as DOMAIN1_224_3_,
        this_.DOMAIN_DESCRIPTION as DOMAIN2_224_3_,
        this_.PARENT_DOMAIN_NAME as PARENT3_224_3_,
        domain3_.DOMAIN_NAME as DOMAIN1_224_0_,
        domain3_.DOMAIN_DESCRIPTION as DOMAIN2_224_0_,
        domain3_.PARENT_DOMAIN_NAME as PARENT3_224_0_,
        u1_.USER_LOGIN_ID as USER1_223_1_,
        u1_.USER_NAME as USER2_223_1_,
        u1_.USER_DOMAIN_NAME as USER3_223_1_,
        domain5_.DOMAIN_NAME as DOMAIN1_224_2_,
        domain5_.DOMAIN_DESCRIPTION as DOMAIN2_224_2_,
        domain5_.PARENT_DOMAIN_NAME as PARENT3_224_2_
    from
        DOMAIN this_
    left outer join
        DOMAIN domain3_
            on this_.PARENT_DOMAIN_NAME=domain3_.DOMAIN_NAME
    inner join
        DOMAIN_USER u1_
            on this_.DOMAIN_NAME=u1_.USER_DOMAIN_NAME
    left outer join
        DOMAIN domain5_
            on u1_.USER_DOMAIN_NAME=domain5_.DOMAIN_NAME
    where
        u1_.USER_NAME=?
     */
    // inner join
    public List<Domain> findDomainsByUserName(final String userName, final boolean enableUsersFetch) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria domainCriteria = getSession().createCriteria(Domain.class, "d")
                    .createAlias("users", "u", CriteriaSpecification.INNER_JOIN)
                    .add(Restrictions.eq("u.name", userName));
                if (enableUsersFetch) {
                    domainCriteria.setFetchMode("users", FetchMode.EAGER);
                }
                return (List<Domain>) domainCriteria.list();
            }
        });
    }

    /*
    select
        this_.DOMAIN_NAME as DOMAIN1_224_2_,
        this_.DOMAIN_DESCRIPTION as DOMAIN2_224_2_,
        this_.PARENT_DOMAIN_NAME as PARENT3_224_2_,
        domain2_.DOMAIN_NAME as DOMAIN1_224_0_,
        domain2_.DOMAIN_DESCRIPTION as DOMAIN2_224_0_,
        domain2_.PARENT_DOMAIN_NAME as PARENT3_224_0_,
        users3_.USER_DOMAIN_NAME as USER3_4_,
        users3_.USER_LOGIN_ID as USER1_4_,
        users3_.USER_LOGIN_ID as USER1_223_1_,
        users3_.USER_NAME as USER2_223_1_,
        users3_.USER_DOMAIN_NAME as USER3_223_1_
    from
        DOMAIN this_
    left outer join
        DOMAIN domain2_
            on this_.PARENT_DOMAIN_NAME=domain2_.DOMAIN_NAME
    left outer join
        DOMAIN_USER users3_
            on this_.DOMAIN_NAME=users3_.USER_DOMAIN_NAME
     */
    // left outer join
    public List<Domain> findDomains(final boolean enableUsersFetch) {
        return (List<Domain>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria domainCriteria = getSession().createCriteria(Domain.class, "d");
                if (enableUsersFetch) {
                    domainCriteria.setFetchMode("users", FetchMode.EAGER);
                }
                return (List<Domain>) domainCriteria.list();
            }
        });
    }

    /*
    select
        d1_.DOMAIN_NAME as y0_,
        count(d1_.DOMAIN_NAME) as y1_
    from
        DOMAIN_USER this_
    left outer join
        DOMAIN d1_
            on this_.USER_DOMAIN_NAME=d1_.DOMAIN_NAME
    group by
        d1_.DOMAIN_NAME
    order by
        d1_.DOMAIN_NAME asc
     */
    // return scalar + use group by + aggregation function COUNT + use order by
    public List<Object[]> findDomainNameUsersCount() {
        return (List<Object[]>) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = getSession().createCriteria(DomainUser.class, "u")
                    .createAlias("userDomain", "d", CriteriaSpecification.LEFT_JOIN)
                    .setProjection( Projections.projectionList()
                        .add( Projections.groupProperty("d.name") )
                        .add( Projections.count("d.name") )
                    )
                    .addOrder( Order.asc("d.name") );
                return (List<Object[]>) criteria.list();
            }
        });
    }

    // right outer join

    // full join
}