package com.shopizer.shop.services.taxservice.model;

public interface Auditable {

    AuditSection getAuditSection();

    void setAuditSection(AuditSection audit);
}

