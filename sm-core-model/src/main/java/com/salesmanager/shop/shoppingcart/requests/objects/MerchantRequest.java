package com.salesmanager.shop.shoppingcart.requests.objects;

public class MerchantRequest {
	
	private int merchantId;
	
	private int cartId;

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

}
