package com.hung.auction.dao;

import java.util.List;

import com.hung.auction.domain.Domain;

public interface DomainDAO {

    public void save(Domain domain);

    public Domain findByName(String name);

    public Domain findByName(String name, boolean enableEagerFetch);

    public List<String> getNamesByPatternStoreProcedure(String pattern);

    public List<String> getNamesByPatternSQL(String pattern);

    public List<String> getParentNamesByPatternSQL(String pattern);

    public List<Domain> getDomainsByPatternStoreProcedure(String pattern);

    public List<Domain> getDomainsByPatternSQL(String pattern);

    public List<Domain> getDomainsByFieldsPatternSQL(String pattern);

    public List<Domain> getDomainsByAliasFieldsPatternSQL(final String pattern);

    public List<Domain> getDomainsByPatternHQL(String pattern);

    public List<Domain> findAll();

    public List<Domain> findAll(boolean enableEagerFetch);

    // -----------------------------------------------------------------------------------------------
    // Mixed scalar-type & entity-type result

    public List<Object[]> getMixedResult(String pattern);

    // --------------------------------------------------------------------
    // Criteria

    public List<Domain> findByUserNameHQL(String userName);

    public List<Domain> findByUserNameHQLFetch(String userName);

    public List<Domain> findByUserNameCriteria(String userName);

    public List<Domain> findByUserNameCriteriaFetch(String userName);

    // --------------------------------------------------------------------
    // SubQuery

    public List<Domain> findByUserNameSubQueryHQL(String userName);

    public List<Domain> findByUserNameSubQueryCriteria(String userName);

    public void deleteByName(String name);
}