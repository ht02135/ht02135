package com.hung.auction.dao2;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.domain.Domain;

public class AbstractQueryDomainDAO extends HibernateDaoSupport implements QueryDomainDAO {

    // inner join
    public List<Domain> findDomainsByUserName(final String userName, final boolean enableUsersFetch) {
        return Collections.EMPTY_LIST;
    }

    // sub-query
    public List<Domain> findDomainsByUserName(final String userName) {
        return Collections.EMPTY_LIST;
    }

    // left outer join
    public List<Domain> findDomains(final boolean enableUsersFetch) {
        return Collections.EMPTY_LIST;
    }

    public List<Object[]> findDomainNameUserCount() {
        return Collections.EMPTY_LIST;
    }

    // right outer join + full join join
    public List<Object[]> findDomainUserNamePair(boolean enableNull) {
        return Collections.EMPTY_LIST;
    }

    // self-join
    public List<Domain> findDomainsWithParentContainUserName(String userName, boolean enableSubQuery) {
        return Collections.EMPTY_LIST;
    }
}