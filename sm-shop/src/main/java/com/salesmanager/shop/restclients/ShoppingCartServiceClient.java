package com.salesmanager.shop.restclients;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartServiceImpl;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.shop.model.shoppingcart.ShoppingCartData;
import com.salesmanager.shop.store.controller.shoppingCart.facade.ShoppingCartFacadeImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingCartServiceClient {
	
	private String url = "http://localhost:8081/shoppingcart/";
	
	@Autowired
	private ShoppingCartServiceImpl shoppingCartService;
	
	@Autowired
	private ShoppingCartFacadeImpl shoppingCartFacade;
	
	
	public ShoppingCart getByCustomer(final Customer customer){
		try {
			ShoppingCart shoppingCart = invokeService(url+"/cart", HttpMethod.POST, parseObjectNode(customer)).getBody();
			
			if (shoppingCart == null) {
				return null;
			}
			return shoppingCartService.getPopulatedShoppingCart(shoppingCart);

		} catch (Exception e) {
			return null;
		}
	}
	
	public ShoppingCartData getByCartIdAndCode(String code, MerchantStore store, Language language){
		try {
			ShoppingCart cartModel = invokeService(url+store.getId()+"/"+code, HttpMethod.GET, null).getBody();
			;
			if(cartModel!=null) {
				ShoppingCartData cart = shoppingCartFacade.getShoppingCartData(cartModel, language);
				return cart;
			}
		} catch( Exception nre) {
			return null;
		}
		return null;

		
	}
	
	public ShoppingCart getByCartIdAndCode(String code, MerchantStore store ){
		try {
			ShoppingCart cartModel = invokeService(url+store.getId()+"/"+code, HttpMethod.GET, null).getBody();
			;
			if(cartModel!=null) {
				ShoppingCart cart = shoppingCartService.getPopulatedShoppingCart(cartModel);
				return cart;
			}
		} catch( Exception nre) {
			return null;
		}
		return null;

		
	}
	
	public ShoppingCart getByCart(final Customer customer){
		
		try {
			ShoppingCart shoppingCart = invokeService("/cart", HttpMethod.POST, parseObjectNode(customer)).getBody();
			
			if (shoppingCart == null) {
				return null;
			}
			return shoppingCartService.getPopulatedShoppingCart(shoppingCart);

		} catch (Exception e) {
			return null;
		}
	}
	
	public ShoppingCart getByMerchant(final Customer customer){
		return invokeService("/merchant", HttpMethod.POST, parseObjectNode(customer)).getBody();
	}
	
	
	public ResponseEntity<ShoppingCart> invokeService(String uri, HttpMethod httpMethod, ObjectNode reqestObject ){
		Log.info("+++++++++++++++++++++++++  Invoking external service:- {}", uri);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json;Charset-UTF-8");
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		  ResponseEntity<ShoppingCart> shoppingCart = restTemplate.exchange(uri, httpMethod, new HttpEntity<>(reqestObject, headers), ShoppingCart.class);
		Log.info("+++++++++++++++++++++++++ Response back from external service:- {}", shoppingCart.getBody().toString());
		Log.info("+++++++++++++++++++++++++  Http Rsponse back from external service:- {}", shoppingCart.getStatusCode().toString());
		return shoppingCart;
	}
	
	
	private ObjectNode parseObjectNode(final Object obj){
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(obj, ObjectNode.class);
	}

}
