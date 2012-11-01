package com.hung.auction.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.hung.auction.domain.Seller;

@Aspect
public class SellerAspect {
	private static Logger log = Logger.getLogger(SellerAspect.class);
	
	// define multiple pointcut
	@Pointcut("execution(* com.hung.auction.service.SellerService.save(com.hung.auction.domain.Seller)) && args(seller)")
	public void save(Seller seller) {
		log.info("save: called");
	}
	
	@Pointcut("execution(* com.hung.auction.service.SellerService.findById(java.lang.Integer)) && args(id)")
	public void findById(Integer id) {
		log.info("save: findById");
	}

	@After("save(seller)")
	public void interceptSave(Seller seller) {
		// could do cross cutting tasks such as sending jms request to mdp
		log.info("interceptSave: do cross cutting tasks, seller="+seller);
	}
	
	@After("findById(id)")
	public void interceptFindById(Integer id) {
		// could do cross cutting tasks such as sending jms request to mdp
		log.info("interceptFindById: do cross cutting tasks, id="+id);
	}
}