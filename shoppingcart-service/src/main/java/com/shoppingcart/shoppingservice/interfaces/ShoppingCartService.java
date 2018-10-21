package com.shoppingcart.shoppingservice.interfaces;

import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
public interface ShoppingCartService extends SalesManagerEntityService<Long, ShoppingCart> {

	ShoppingCart findOne(Long id);
	
	ShoppingCart findByCode(String code);
	
	ShoppingCart findById(Integer merchantId, Long id);
	
	ShoppingCart findByCode(Integer merchantId, String code);
	
	ShoppingCart findByCustomer(Long customerId);
	
}
