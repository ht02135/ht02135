package com.hung.auction.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hung.auction.dao.SellerDAO;
import com.hung.auction.domain.Seller;

public class HibernateSellerDAO extends HibernateDaoSupport implements SellerDAO {
	
	private static Logger log = Logger.getLogger(HibernateSellerDAO.class);

    public void save(Seller seller) {
    	log.info("seller="+seller);
    	getHibernateTemplate().save(seller);
    	if (seller.getId() != null) {
    		log.info("seller.getId()="+seller.getId());
    	}
    }
    
    public Seller findById(final Integer id) {
    	return (Seller) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from Seller s where s.id = :id");
				query.setParameter("id", id);
				return (Seller) query.uniqueResult();
			}
		});
    }
    
    public List<Seller> findAll() {
    	return (List<Seller>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from Seller s");
				return (List<Seller>) query.list();
			}
		});
    }
    
    public List<Seller> findByName(final String name) {
    	return (List<Seller>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from Seller s where s.name like :name");
				query.setParameter("name", '%' + name + '%');
				return (List<Seller>) query.list();
			}
		});
    }
}