package com.hung.auction.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.hung.auction.dao.BidDAO;
import com.hung.auction.domain.Bid;
import com.hung.auction.domain.Bidder;

@Repository("bidDAO")
public class HibernateBidDAO extends HibernateDaoSupport implements BidDAO {

    public void save(Bid bid) {
    	getHibernateTemplate().save( bid );
    }
    
    @Autowired
    public HibernateBidDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super();
        setSessionFactory(sessionFactory);
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