package com.hung.auction.dao2;

import java.util.List;

import com.hung.auction.domain.Domain;

public interface QueryDomainDAO {

    // inner join
    List<Domain> findDomainsByUserName(String userName, boolean enableUsersFetch);

    // sub-query
    List<Domain> findDomainsByUserName(String userName);

    // left outer join
    List<Domain> findDomains(boolean enableUsersFetch);

    // return scalar + use group by + aggregation function COUNT, only meaningful if return scalar value via projection
    // return list of ([domainName1,usersInDomain],...])
    List<Object[]> findDomainNameUserCount();

    // right outer join + full join join, in term of OO, only meaningful if return scalar value via projection
    List<Object[]> findDomainUserNamePair(boolean enableNull);

    // self-join
    List<Domain> findDomainsWithParentContainUserName(String userName, boolean enableSubQuery);

    /*
    1>The HAVING clause is used in combination with the GROUP BY clause:      
    SELECT column1, column2, ... column_n, aggregate_function (expression)
    FROM tables
    WHERE predicates
    GROUP BY column1, column2, ... column_n
    HAVING condition1 ... condition_n;
    
    2>date comparison 
    SELECT productid FROM product where purchase_date > sysdate-30;
    */
}