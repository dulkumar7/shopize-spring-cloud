package com.shoppingcart.shoppingservice.support.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

public class ShoppingCartAttributeItemResponse  implements AuditableResponse {


	private static final long serialVersionUID = 1L;

	private Long id;

	private AuditSectionResponse auditSection = new AuditSectionResponse();
	

	
	private Long productAttributeId;
	
	private ProductAttributeResponse productAttribute;
	

	
	private ShoppingCartItemResponse shoppingCartItem;
	
	public ShoppingCartAttributeItemResponse(ShoppingCartItemResponse shoppingCartItem, ProductAttributeResponse productAttribute) {
		this.shoppingCartItem = shoppingCartItem;
		this.productAttribute = productAttribute;
		this.productAttributeId = productAttribute.getId();
	}
	
	public ShoppingCartAttributeItemResponse() {

	}
	
	

	public ShoppingCartItemResponse getShoppingCartItem() {
		return shoppingCartItem;
	}

	public void setShoppingCartItem(ShoppingCartItemResponse shoppingCartItem) {
		this.shoppingCartItem = shoppingCartItem;
	}

	@Override
	public AuditSectionResponse getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSectionResponse audit) {
		this.auditSection = audit;
		
	}



	public void setProductAttributeId(Long productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

	public Long getProductAttributeId() {
		return productAttributeId;
	}

	public void setProductAttribute(ProductAttributeResponse productAttribute) {
		this.productAttribute = productAttribute;
	}

	public ProductAttributeResponse getProductAttribute() {
		return productAttribute;
	}


}
