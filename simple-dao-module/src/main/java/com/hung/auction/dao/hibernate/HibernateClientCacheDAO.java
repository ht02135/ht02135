package com.hung.auction.dao.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hung.auction.dao.ClientCacheDAO;
import com.hung.auction.domain.ClientCache;

public class HibernateClientCacheDAO extends HibernateDaoSupport implements ClientCacheDAO {

	private static Logger log = Logger.getLogger(HibernateClientCacheDAO.class);

	public void save(ClientCache clientCache) {
		getHibernateTemplate().save(clientCache);
	}

	public List<ClientCache> getClientCaches(final String loginId, final String entityType) {
    	return (List<ClientCache>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = getSession().createQuery("from ClientCache cc where cc.id.loginId = :loginId and cc.id.entityType = :entityType");
				query.setParameter("loginId", loginId);
				query.setParameter("entityType", entityType);
				return (List<ClientCache>) query.list();
			}
		});
	}
}