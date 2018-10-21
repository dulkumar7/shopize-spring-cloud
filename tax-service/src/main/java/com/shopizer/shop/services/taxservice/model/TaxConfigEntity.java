package com.shopizer.shop.services.taxservice.model;

import javax.persistence.*;

@Entity
@Table( name = "TaxConfigEntity", schema = "tax-service-database")
public class TaxConfigEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Integer id;

    private String merchantConfigValue;

    private String storeId;

    private String taxConfigKey;

    public TaxConfigEntity() { }

    public TaxConfigEntity(Integer id, String merchantConfigValue, String storeId, String taxConfigKey) {
        this.id = id;
        this.merchantConfigValue = merchantConfigValue;
        this.storeId = storeId;
        this.taxConfigKey = taxConfigKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantConfigValue() {
        return merchantConfigValue;
    }

    public void setMerchantConfigValue(String merchantConfigValue) {
        this.merchantConfigValue = merchantConfigValue;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTaxConfigKey() {
        return taxConfigKey;
    }

    public void setTaxConfigKey(String taxConfigKey) {
        this.taxConfigKey = taxConfigKey;
    }
}
