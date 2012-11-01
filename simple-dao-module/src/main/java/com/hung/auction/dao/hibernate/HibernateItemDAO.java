package com.hung.auction.dao.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hung.auction.dao.ItemDAO;
import com.hung.auction.domain.Item;

public class HibernateItemDAO extends HibernateDaoSupport implements ItemDAO {

    public void save(Item item) {
    	getHibernateTemplate().save( item );
    }

}