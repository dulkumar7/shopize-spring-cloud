package com.shoppingcart.shoppingservice.support.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

public class ShoppingCartItemResponse implements AuditableResponse, Serializable {


	private static final long serialVersionUID = 1L;

	private Long id;
	
	private ShoppingCartResponse shoppingCart;

	private Integer quantity = new Integer(1);


	private AuditSectionResponse auditSection = new AuditSectionResponse();
	
	private Long productId;
	
	private boolean productVirtual;

	private Set<ShoppingCartAttributeItemResponse> attributes = new HashSet<ShoppingCartAttributeItemResponse>();
	
	private BigDecimal itemPrice;//item final price including all rebates
	
	private BigDecimal subTotal;//item final price * quantity
	
	private FinalPriceResponse finalPrice;//contains price details (raw prices)
	

//	private Product product;
	
	private boolean obsolete = false;




	public ShoppingCartItemResponse(ShoppingCartResponse shoppingCart) {
//		this.product = product;
//		this.productId = product.getId();
		this.quantity = 1;
		this.shoppingCart = shoppingCart;
		
	}
	
//	public ShoppingCartItem(Product product) {
//		this.product = product;
//		this.productId = product.getId();
//		this.quantity = 1;
//
//	}
	
	public ShoppingCartItemResponse() {
		
	}

	public AuditSectionResponse getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSectionResponse audit) {
		this.auditSection = audit;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		
	}



	public void setAttributes(Set<ShoppingCartAttributeItemResponse> attributes) {
/*	    if(attributes==null) {
	    	this.attributes = null;
	    	return;
	    }
		this.attributes.clear();
	    this.attributes.addAll( attributes );*/
		this.attributes = attributes;
	}

	public Set<ShoppingCartAttributeItemResponse> getAttributes() {
		return attributes;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}



	public ShoppingCartResponse getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCartResponse shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}

//	public void setProduct(Product product) {
//		this.product = product;
//	}
//
//	public Product getProduct() {
//		return product;
//	}
//	
//	public void addAttributes(ShoppingCartAttributeItem shoppingCartAttributeItem)
//	{
//	    this.attributes.add(shoppingCartAttributeItem);
//	}
//	
//	public void removeAttributes(ShoppingCartAttributeItem shoppingCartAttributeItem)
//	{
//	    this.attributes.remove(shoppingCartAttributeItem);
//	}

	public void removeAllAttributes(){
		this.attributes.removeAll(Collections.EMPTY_SET);
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

//	public void setFinalPrice(FinalPrice finalPrice) {
//		this.finalPrice = finalPrice;
//	}
//
//	public FinalPrice getFinalPrice() {
//		return finalPrice;
//	}
	
	public boolean isObsolete() {
		return obsolete;
	}

	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}
	

	public boolean isProductVirtual() {
		return productVirtual;
	}

	public void setProductVirtual(boolean productVirtual) {
		this.productVirtual = productVirtual;
	}

}
