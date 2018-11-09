package com.salesmanager.shop.restclients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShoppingCartServiceClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${SHOPPINGCART_SERVICE_URL}")
	private String url;

	public ShoppingCart getByCustomer(final Customer customer) {

		ResponseEntity<ShoppingCart> cart = invokeService(url + "customer?customerId=" + customer.getId(),
				HttpMethod.GET, null);
		if (null != cart) {
			return cart.getBody();
		}
		return null;
	}

	public ShoppingCart getByCartIdAndCode(String code, MerchantStore store) {
		ResponseEntity<ShoppingCart> cart = invokeService(url + +store.getId() + "/" + code, HttpMethod.GET, null);
		if (null != cart) {
			return cart.getBody();
		}
		return null;
	}

	public ShoppingCart getByMerchant(final Customer customer) {
		ResponseEntity<ShoppingCart> cart = invokeService(url + "merchant", HttpMethod.POST, parseObjectNode(customer));
		if (null != cart) {
			return cart.getBody();
		}
		return null;
	}

	public void deleteShoppingCart(final long itemId) {
		invokeService(url + itemId, HttpMethod.DELETE, null);
	}

	public ResponseEntity<ShoppingCart> invokeService(String uri, HttpMethod httpMethod, ObjectNode reqestObject) {
		Log.info("+++++++++++++++++++++++++  Invoking external service:- ", uri);
		Log.info("+++++++++++++++++++++++++  service method:- ", httpMethod.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-", "application/json;Charset-UTF-8");
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ResponseEntity<ShoppingCart> shoppingCart = restTemplate.exchange(uri, httpMethod,
				new HttpEntity<>(reqestObject, headers), ShoppingCart.class);
		if (null != shoppingCart && null != shoppingCart.getBody()) {
			Log.info("+++++++++++++++++++++++++ Response back from external service:- {}",
					shoppingCart.getBody().toString());
			Log.info("+++++++++++++++++++++++++  Http Rsponse back from external service:- {}",
					shoppingCart.getStatusCode().toString());
			return shoppingCart;

		}
		return null;
	}

	private ObjectNode parseObjectNode(final Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(obj, ObjectNode.class);
	}

}
