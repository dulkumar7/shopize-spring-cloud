package com.shoppingcart.shoppingservice.restcontrollers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.shop.shoppingcart.requests.objects.CartRequest;
import com.salesmanager.shop.shoppingcart.requests.objects.CustomerRequest;
import com.salesmanager.shop.shoppingcart.requests.objects.MerchantRequest;
import com.shoppingcart.shoppingservice.services.ShopCartService;
import com.shoppingcart.shoppingservice.support.entities.ShoppingCartResponse;


@RestController
@RequestMapping("/shoppingcart")
public class ShoppingcartRestController {
	
	/*@Autowired 
	ObjectMapper objectMapper;*/
	
	@Autowired
	private ShopCartService shopCartService;
	
	/*@RequestMapping(value="/customer-id/{customerId}", method=RequestMethod.GET)
	public ShoppingCartResponse getShoppingcartByCustomer(@PathVariable(value ="customerId") String customerId) {
		return shopCartService.getShoppingcartByCustomer(customerId);
	}
	
	@RequestMapping(value="/code/{code}", method=RequestMethod.GET)
	public ShoppingCartResponse getShoppingcartByCode(@PathVariable(value ="code") String code) {
		return shopCartService.getShoppingcartByCode(code);
	}
	@RequestMapping(value="/merchant-id/{merchantId}/cart-id/{cartId}", method=RequestMethod.GET)
	public ShoppingCartResponse getById(@PathVariable(value ="merchantId") int merchantId, @PathVariable(value ="cartId") String cartId) {
		return shopCartService.getById(merchantId, cartId);
	}
	@RequestMapping(value="/cart-id/{cartId}", method=RequestMethod.GET)
	public ShoppingCartResponse getShoppingcartByOne(@PathVariable(value ="cartId") String cartId) {
		return shopCartService.getShoppingcartByOne(cartId);
	}
	*/
	
	/*@PostMapping("/get-cart-by-customer")
	public ShoppingCart getShoppingCartByCustomer(@RequestBody Customer customer) {
		
	return null;
		
	}
	
	@PostMapping("get-cart-by-store-and-id")
	public ShoppingCart getShoppingCartByStoreIdAndStore(@RequestBody Integer shoppingCartId, @RequestBody MerchantStore store) {
		return null;
	}
	
	@PostMapping("get-cart-by-store-and-language")
	public ShoppingCart getCartByStoreAndLanguage(@RequestBody ShoppingCart cart, @RequestBody MerchantStore store, @RequestBody Language language) {
		return null;
	}*/
	
	
	
	@RequestMapping(value="/{id}/{code}", method=RequestMethod.GET)
	public ShoppingCartResponse getShoppingCartByCode(@PathVariable(value ="id") int id, @PathVariable(value ="code") String code) {
		return shopCartService.getShoppingcartByCode(id, code);
	}
	
	
	@RequestMapping(value="/cart", method=RequestMethod.POST)
	public ShoppingCartResponse getShoppingcartByCartId(@Valid @RequestBody CartRequest cartId) {
		return shopCartService.getByShoppingCartId(cartId);
	}
	
	@RequestMapping(value="/customer", method=RequestMethod.POST)
	public ShoppingCartResponse getByCustomer(@Valid @RequestBody  CustomerRequest customerRequest) {
		return shopCartService.getShoppingCartByCustomer(customerRequest);
	}
	
	
	@RequestMapping(value="/merchant", method=RequestMethod.POST)
	public ShoppingCartResponse getById(@Valid @RequestBody MerchantRequest merchantRequest) {
		return shopCartService.getByShoppingCartMerchantId(merchantRequest);
	}

	
	
}