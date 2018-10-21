package com.salesmanager.shop.shoppingcart.requests.objects;

public class CustomerRequest {

	private int customerId;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public CustomerRequest(final int customerId){
		this.customerId = customerId;
	}
}
