package com.shoppingcart.shoppingservice.support.entities;

public interface AuditableResponse {
	
	AuditSectionResponse getAuditSection();
	
	void setAuditSection(AuditSectionResponse audit);
}
