package com.hung.auction.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hung.auction.dao.BidDAO;
import com.hung.auction.domain.Bid;
import com.hung.auction.domain.Bidder;

public class HibernateBidDAO extends HibernateDaoSupport implements BidDAO {

    public void save(Bid bid) {
    	getHibernateTemplate().save( bid );
    }

    public List<Bid> findByBidder(final Bidder bidder) {
    	return (List<Bid>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from Bid b where b.bidder = :bidder");
				query.setParameter("bidder", bidder);
				return new ArrayList<Bidder>( query.list() );
			}
		});
    }
    
    public Bid findById(final Integer id) {
    	return (Bid) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from Bid b where b.id = :id");
				query.setParameter("id", id);
				return (Bid) query.uniqueResult();
			}
		});
    }
}