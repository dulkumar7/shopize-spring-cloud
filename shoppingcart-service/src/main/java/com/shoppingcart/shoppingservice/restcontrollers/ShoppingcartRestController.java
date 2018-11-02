package com.shoppingcart.shoppingservice.restcontrollers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.salesmanager.core.business.exception.ServiceException;
//import com.salesmanager.shop.restclients.ShoppingCartServiceClient;
import com.salesmanager.shop.shoppingcart.requests.objects.CartRequest;
import com.salesmanager.shop.shoppingcart.requests.objects.CustomerRequest;
import com.salesmanager.shop.shoppingcart.requests.objects.MerchantRequest;
import com.shoppingcart.shoppingservice.services.ShopCartService;
import com.shoppingcart.shoppingservice.support.entities.ShoppingCartResponse;
import com.squareup.okhttp.Response;

@RestController
@RequestMapping
public class ShoppingcartRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingcartRestController.class);

	@Autowired
	private ShopCartService shopCartService;

	@GetMapping("/{id}/{code}")
	 //@HystrixCommand(commandKey = "shoppingCartFromRepo", fallbackMethod =
	// "retriveShoppingCartByCode")
	public ShoppingCartResponse getShoppingCartByCode(@PathVariable(value = "id") int id,
			@PathVariable(value = "code") String code) throws JsonProcessingException {
		LOGGER.info("START: ShoppingcartRestController.getShoppingCartByCode(): id=" + id + " code=" + code);
		ShoppingCartResponse result = shopCartService.getByCartIdAndCode(code, id);
		LOGGER.info("END: ShoppingcartRestController.getShoppingCartByCode(): result=" + result);
		return result;

	}

	@DeleteMapping("/{itemId}")
	// "retriveShoppingCartByCode")
	public void removeShoppingCartByCode(@PathVariable(value = "itemId") String itemId) throws JsonProcessingException {

		LOGGER.info("START ++++++++++++++++++: Serivce to delete shopping cart url: - '/{itemId}' ");
		shopCartService.deleteShoppingCartItem(itemId);
		LOGGER.info("END ++++++++++++++++++: deleted shopping cart with id: - ", itemId);

	}

	/*
	 * @RequestMapping(value = "/cart", method = RequestMethod.POST) public
	 * ShoppingCartResponse getShoppingcartByCartId(@Valid @RequestBody CartRequest
	 * cartId) { return shopCartService.getByShoppingCartId(cartId); }
	 * 
	 * @RequestMapping(value = "/customer", method = RequestMethod.POST) public
	 * ShoppingCartResponse getByCustomer(@Valid @RequestBody CustomerRequest
	 * merchantRequest) { ShoppingCartResponse response = null; try { response =
	 * shopCartService.getShoppingCartByCustomer(merchantRequest); } catch
	 * (Exception e) {
	 * logger.error("Exception in ShoppingCartRestController.getCustomer()", e); }
	 * return response; }
	 */

	@GetMapping("/customer")
	// @HystrixCommand(commandKey = "getShoppingCartByCustomerFromRepo",
	// fallbackMethod = "retriveShoppingCartByCustomerFallBack")
	public ShoppingCartResponse getByCustomerId(@RequestParam String customerId) {
		LOGGER.info("Start: ShoppingcartRestController.getByCustomerId(): customerId=" + customerId);
		ShoppingCartResponse respon = shopCartService.getShoppingcartByCustomer(customerId);
		LOGGER.info("END: ShoppingcartRestController.getByCustomerId(): result=" + respon);
		return respon;
	}

	/*
	 * @RequestMapping(value = "/merchant", method = RequestMethod.POST) public
	 * ShoppingCartResponse getById(@Valid @RequestBody MerchantRequest
	 * merchantRequest) { return
	 * shopCartService.getByShoppingCartMerchantId(merchantRequest); }
	 */

	/*
	 * @DeleteMapping("/customer") //@HystrixCommand(commandKey =
	 * "getShoppingCartByCustomerFromRepo", fallbackMethod =
	 * "retriveShoppingCartByCustomerFallBack") public void
	 * DeleteByCustomerId(@RequestParam String customerId) { LOGGER.
	 * info("Start: ShoppingcartRestController.removeShoppingcartByCustomer(): customerId="
	 * + customerId); shopCartService.removeShoppingcartByCustomer(customerId);
	 * LOGGER.
	 * info("END: ShoppingcartRestController.removeShoppingcartByCustomer(): Response="
	 * + shopCartService.removeShoppingcartByCustomer(customerId));
	 * 
	 * }
	 */

	public ShoppingCartResponse retriveShoppingCartByCode(String code) {

		return shopCartService.getShoppingcartByCodeFallback(code);
	}

	public ShoppingCartResponse retriveShoppingCartByCustomer(CustomerRequest customerRequest) {
		return shopCartService.getByShoppingCartIdFallBack(customerRequest);
	}
}