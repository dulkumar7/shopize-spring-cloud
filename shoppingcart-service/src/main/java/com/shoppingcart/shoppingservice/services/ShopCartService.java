package com.shoppingcart.shoppingservice.services;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.shop.shoppingcart.requests.objects.CartRequest;
import com.salesmanager.shop.shoppingcart.requests.objects.CustomerRequest;
import com.salesmanager.shop.shoppingcart.requests.objects.MerchantRequest;
import com.shoppingcart.shoppingservice.entities.ShoppingCart;
import com.shoppingcart.shoppingservice.entities.ShoppingCartItem;
import com.shoppingcart.shoppingservice.repository.ShoppingCartRepository;
import com.shoppingcart.shoppingservice.support.entities.ShoppingCartResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopCartService {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	public ShoppingCartResponse getById(int merchantId, String id) {
		if (StringUtils.isNotBlank(id)) {
			return parseShoppingCartResp(shoppingCartRepository.findById(merchantId, Long.valueOf(id)));
		}
		return null;
	}

	public ShoppingCartResponse getShoppingcartByCustomer(String id) {
		ShoppingCartResponse shoppingCartRes = null;
		ShoppingCart shoppingCart = null;
		if (StringUtils.isNotBlank(id)) {
			shoppingCart = shoppingCartRepository.findByCustomer(Long.valueOf(id));
			shoppingCartRes = parseShoppingCartResp(shoppingCart);
		}

		if (shoppingCartRes != null && shoppingCartRes.isObsolete()) {
			shoppingCartRepository.delete(shoppingCart);
			return null;
		} else {
			return shoppingCartRes;
		}
	}

	public ShoppingCartResponse getShoppingcartByCode(String code) {
		return parseShoppingCartResp(shoppingCartRepository.findByCode(code));
	}

	public ShoppingCartResponse getShoppingcartByOne(String id) {
		if (StringUtils.isNotBlank(id)) {
			return parseShoppingCartResp(shoppingCartRepository.findOne(Long.valueOf(id)));
		}

		return null;
	}

	/**
	 * Converted to post
	 * 
	 * @param id
	 * @return
	 */
	public ShoppingCartResponse getByShoppingCartId(CartRequest id) {
		return parseShoppingCartResp(shoppingCartRepository.findOne(Long.valueOf(id.getCartId())));

	}

	public ShoppingCartResponse getShoppingCartByCustomer(CustomerRequest id) {
		ShoppingCartResponse shoppingCartRes = null;
		ShoppingCart shoppingCart = null;
		if (0 != id.getCustomerId()) {
			shoppingCart = shoppingCartRepository.findByCustomer(Long.valueOf(id.getCustomerId()));
			shoppingCartRes = parseShoppingCartResp(shoppingCart);
		}

		if (shoppingCartRes != null && shoppingCartRes.isObsolete()) {
			shoppingCartRepository.delete(shoppingCart);
			return null;
		} else {
			return shoppingCartRes;
		}
	}

	public ShoppingCartResponse getByShoppingCartMerchantId(MerchantRequest merchantRequest) {
		return parseShoppingCartResp(shoppingCartRepository.findById(merchantRequest.getMerchantId(),
				Long.valueOf(merchantRequest.getCartId())));
	}

	public ShoppingCartResponse getShoppingcartByCode(int id, String code) {
		return parseShoppingCartResp(shoppingCartRepository.findByCode(id, code));
	}

	
	private ShoppingCartResponse parseShoppingCartResp(ShoppingCart cart) {
		ShoppingCartResponse response = null;
		Set<ShoppingCartItem> lineItems = new HashSet<ShoppingCartItem>();
		if (null != cart) {
			response = new ShoppingCartResponse();
			response.setId(cart.getId());

			response.setAuditSection(cart.getAuditSection());
			response.setCustomerId(cart.getCustomerId());
			lineItems = cart.getLineItems();
			response.setLineItems(lineItems);
			response.setMerchantStore(cart.getMerchantStore());
			response.setShoppingCartCode(cart.getShoppingCartCode());
		}
		return response;
	}

}
