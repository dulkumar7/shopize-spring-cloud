package com.shoppingcart.shoppingservice.services;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.shoppingcart.ShoppingCartRepository;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartServiceImpl;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.shop.shoppingcart.requests.objects.CartRequest;
import com.salesmanager.shop.shoppingcart.requests.objects.CustomerRequest;
import com.salesmanager.shop.shoppingcart.requests.objects.MerchantRequest;
import com.shoppingcart.shoppingservice.entities.AuditSection;
import com.shoppingcart.shoppingservice.entities.Product;
import com.shoppingcart.shoppingservice.entities.ShoppingCartItem;
import com.shoppingcart.shoppingservice.support.entities.ShoppingCartResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopCartService {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Autowired
	private ShoppingCartServiceImpl shoppingCartService;
	

	/*
	 * public ShoppingCartResponse getById(int merchantId, String id) { if
	 * (StringUtils.isNotBlank(id)) { return
	 * parseShoppingCartResp(shoppingCartRepository.findById(merchantId,
	 * Long.valueOf(id))); } return null; }
	 */

	// getByCustomer
	public ShoppingCartResponse getShoppingcartByCustomer(String id) {
		ShoppingCart shoppingCart = null;
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			Customer customer = new Customer();
			customer.setId(Long.valueOf(id));
			try {
				shoppingCart = shoppingCartService.getByCustomer(customer);
			} catch (ServiceException e) {
			}

		}
		return parseShoppingCartResp(shoppingCart);

	}

	// getByCode
	public ShoppingCartResponse getByCartIdAndCode(String code, int merchantId) {
		ShoppingCart cart = null;
		MerchantStore store = new MerchantStore();
		store.setId(Integer.valueOf(merchantId));
		try {
			cart = shoppingCartService.getByCode(code, store);
		} catch (ServiceException e) {
		}
		return parseShoppingCartResp(cart);
	}

	// removeByCode
	public void deleteShoppingCartItem(String itemId) {
		shoppingCartService.deleteShoppingCartItem(Long.valueOf(itemId));
	}

	
	// RemoveByCustomer
		public ShoppingCartResponse removeShoppingcartByCustomer(String id) {
			ShoppingCart shoppingCart = null;
			if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
				Customer customer = new Customer();
				customer.setId(Long.valueOf(id));
			
					try {
						shoppingCart = shoppingCartService.getByCustomer(customer);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
					
					shoppingCartRepository.delete(shoppingCart);
							
			}
			return null;
		}
	public ShoppingCartResponse getShoppingcartByCodeFallback(String code) {

		return parseShoppingCartResp(shoppingCartRepository.findByCode(code));
	}

	/*
	 * public ShoppingCartResponse getShoppingcartByOne(String id) { if
	 * (StringUtils.isNotBlank(id)) { return
	 * parseShoppingCartResp(shoppingCartRepository.findOne(Long.valueOf(id))); }
	 * 
	 * return null; }
	 */

	/**
	 * Converted to post
	 * 
	 * @param id
	 * @return
	 */
	public ShoppingCartResponse getByShoppingCartIdFallBack(CustomerRequest id) {
		return parseShoppingCartResp(shoppingCartRepository.findByCustomer(Long.valueOf(id.getCustomerId())));

	}

	/*
	 * public ShoppingCartResponse getShoppingCartByCustomer(CustomerRequest id)
	 * throws Exception { ShoppingCartResponse shoppingCartRes = null; ShoppingCart
	 * shoppingCart = null; if (0 != id.getCustomerId()) { shoppingCart =
	 * shoppingCartRepository.findByCustomer(Long.valueOf(id.getCustomerId()));
	 * ShoppingCart shopCart =
	 * shoppingCartService.getPopulatedShoppingCart(shoppingCart); shoppingCartRes =
	 * parseShoppingCartResp(shopCart); }
	 * 
	 * if (shoppingCartRes != null && shoppingCartRes.isObsolete()) {
	 * shoppingCartRepository.delete(shoppingCart); return null; } else { return
	 * shoppingCartRes; } }
	 */

	public ShoppingCartResponse getByShoppingCartMerchantId(MerchantRequest merchantRequest) {

		ShoppingCart shoppingCart = shoppingCartRepository.findById(merchantRequest.getMerchantId(),
				Long.valueOf(merchantRequest.getCartId()));

		return parseShoppingCartResp(shoppingCart);
	}

	public ShoppingCartResponse getShoppingcartByCode(int id, String code) {
		ShoppingCart cart = null;
		MerchantStore store = new MerchantStore();
		try {
			cart = shoppingCartService.getByCode(code, store);
		} catch (ServiceException e) {
		}
		return parseShoppingCartResp(cart);
	}

	private ShoppingCartResponse parseShoppingCartResp(ShoppingCart cart) {
		ShoppingCartResponse response = null;
		Set<ShoppingCartItem> lineItems = new HashSet<ShoppingCartItem>();
		if (null != cart) {
			response = new ShoppingCartResponse();
			response.setId(cart.getId());
			AuditSection auditSection = new AuditSection();
			try {
				BeanUtils.copyProperties(auditSection, cart.getAuditSection());

				response.setAuditSection(auditSection);
				response.setCustomerId(cart.getCustomerId());

				if (CollectionUtils.isNotEmpty(cart.getLineItems())) {
					for (com.salesmanager.core.model.shoppingcart.ShoppingCartItem itm : cart.getLineItems()) {
						ShoppingCartItem item = new ShoppingCartItem();
						com.shoppingcart.shoppingservice.entities.ShoppingCart indCart = new com.shoppingcart.shoppingservice.entities.ShoppingCart();
						indCart.setId(itm.getShoppingCart().getId());
						item.setId(itm.getId());
						item.setShoppingCart(indCart);
						item.setItemPrice(itm.getItemPrice());
						item.setProductId(itm.getProductId());
						Product product = new Product();
						product.setSku(itm.getProduct().getSku());
						item.setProduct(product);
						product.setId(itm.getProduct().getId());
						lineItems.add(item);
					}

				}

				com.shoppingcart.shoppingservice.entities.MerchantStore store = new com.shoppingcart.shoppingservice.entities.MerchantStore();

//					BeanUtils.copyProperties(store, cart.getMerchantStore());
				store.setId(cart.getMerchantStore().getId());
				response.setLineItems(lineItems);
				response.setMerchantStore(store);
				response.setShoppingCartCode(cart.getShoppingCartCode());
			} catch (IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return response;
	}

}
