package com.shopizer.shop.services.taxservice.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class TaxConfiguration implements JSONAware {

    private TaxBasisCalculation taxBasisCalculation = TaxBasisCalculation.SHIPPINGADDRESS;

    private boolean collectTaxIfDifferentProvinceOfStoreCountry = true;
    private boolean collectTaxIfDifferentCountryOfStoreCountry = false;

    public TaxConfiguration() {
        super();
    }

    @Override
    public String toJSONString() {
        JSONObject data = new JSONObject();
        data.put("taxBasisCalculation", this.getTaxBasisCalculation().name());

        return data.toJSONString();
    }

    public void setTaxBasisCalculation(TaxBasisCalculation taxBasisCalculation) {
        this.taxBasisCalculation = taxBasisCalculation;
    }

    public TaxBasisCalculation getTaxBasisCalculation() {
        return taxBasisCalculation;
    }

    public void setCollectTaxIfDifferentProvinceOfStoreCountry(
            boolean collectTaxIfDifferentProvinceOfStoreCountry) {
        this.collectTaxIfDifferentProvinceOfStoreCountry = collectTaxIfDifferentProvinceOfStoreCountry;
    }

    public boolean isCollectTaxIfDifferentProvinceOfStoreCountry() {
        return collectTaxIfDifferentProvinceOfStoreCountry;
    }

    public void setCollectTaxIfDifferentCountryOfStoreCountry(
            boolean collectTaxIfDifferentCountryOfStoreCountry) {
        this.collectTaxIfDifferentCountryOfStoreCountry = collectTaxIfDifferentCountryOfStoreCountry;
    }

    public boolean isCollectTaxIfDifferentCountryOfStoreCountry() {
        return collectTaxIfDifferentCountryOfStoreCountry;
    }

}
