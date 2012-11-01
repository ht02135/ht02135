package com.hung.auction.dao.strategy;

import org.springframework.orm.hibernate3.HibernateTemplate;

public interface IPersistenceStrategy {

	public Object persist(HibernateTemplate hibernateTemplate, Object object);
}