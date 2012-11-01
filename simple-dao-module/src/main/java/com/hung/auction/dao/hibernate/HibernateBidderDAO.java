package com.hung.auction.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hung.auction.dao.BidderDAO;
import com.hung.auction.domain.Bidder;

public class HibernateBidderDAO extends HibernateDaoSupport implements BidderDAO {

    public void save(Bidder bidder) {
    	getHibernateTemplate().save( bidder );
    }

    public List<Bidder> findByName(final String name) {
    	return (List<Bidder>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from Bidder b where b.name = :name");
				query.setParameter("name", name);
				return new ArrayList<Bidder>( query.list() );
			}
		});
    }
}