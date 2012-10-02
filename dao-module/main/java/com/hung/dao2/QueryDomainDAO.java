package com.hung.auction.dao2;

import java.util.List;

import com.hung.auction.domain.Domain;

public interface QueryDomainDAO {

    // inner join
    List<Domain> findDomainsByUserName(String userName, boolean enableUsersFetch);

    // left outer join
    List<Domain> findDomains(final boolean enableUsersFetch);

    // return scalar + use group by + aggregation function COUNT
    List<Object[]> findDomainNameUsersCount();

    // right outer join

    // full join (not usually useful)

}