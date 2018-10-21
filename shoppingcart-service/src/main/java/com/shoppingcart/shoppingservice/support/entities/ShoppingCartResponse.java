package com.shoppingcart.shoppingservice.support.entities;

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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shoppingcart.shoppingservice.entities.AuditSection;
import com.shoppingcart.shoppingservice.entities.MerchantStore;
import com.shoppingcart.shoppingservice.entities.ShoppingCartItem;


/**
 * <p>Shopping cart is responsible for storing and carrying
 * shopping cart information.Shopping Cart consists of {@link ShoppingCartItem}
 * which represents individual lines items associated with the shopping cart</p> 
 * @author Umesh Awasthi
 * version 2.0
 *
 */
public class ShoppingCartResponse   {

	
	private AuditSection auditSection = new AuditSection();
	
	private Long id;
	
	/**
	 * Will be used to fetch shopping cart model from the controller
	 * this is a unique code that should be attributed from the client (UI)
	 * 
	 */
	private String shoppingCartCode;
	
	private Set<ShoppingCartItem> lineItems = new HashSet<ShoppingCartItem>();
	
	private MerchantStore merchantStore;
	
	private Long customerId;
	
	private boolean obsolete = false;//when all items are obsolete
    
//	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

//	@Override
	public void setAuditSection(AuditSection audit) {
		this.auditSection = audit;
		
	}
	
//	@Override
	public Long getId() {
		return id;
	}

//	@Override
	public void setId(Long id) {
		this.id = id;
		
	}
	

	public boolean isObsolete() {
		return obsolete;
	}

	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}

	public Set<ShoppingCartItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Set<ShoppingCartItem> lineItems) {
		this.lineItems = lineItems;
	}

    public String getShoppingCartCode()
    {
        return shoppingCartCode;
    }

    public void setShoppingCartCode( String shoppingCartCode )
    {
        this.shoppingCartCode = shoppingCartCode;
    }


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setMerchantStore(MerchantStore merchantStore) {
		this.merchantStore = merchantStore;
	}

	public MerchantStore getMerchantStore() {
		return merchantStore;
	}

}
