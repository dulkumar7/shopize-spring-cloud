package com.shopizer.shop.services.taxservice.restcontroller;

import com.shopizer.shop.services.taxservice.mapper.TaxAppObjectMapper;
import com.shopizer.shop.services.taxservice.model.MerchantConfiguration;
import com.shopizer.shop.services.taxservice.model.MerchantStore;
import com.shopizer.shop.services.taxservice.model.TaxConfiguration;
import com.shopizer.shop.services.taxservice.service.TaxAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rest/tax-service/tax-configuration")
public class TaxConfigurationRestController {

    @Autowired
    private TaxAppService taxAppService;

    @Autowired
    private TaxAppObjectMapper taxAppObjectMapper;

    @PostMapping("/edit")
    public TaxConfiguration getTaxConfiguration(@RequestBody Integer storeId) {
        TaxConfiguration taxConfiguration = taxAppService.getTaxConfiguration(storeId);
        if(taxConfiguration == null) {
            return new TaxConfiguration();
        }
        return taxConfiguration;
    }

    @PostMapping("/save")
    public String saveTaxConfiguration(@RequestBody Map<String, String> reqBody) {

        TaxConfiguration taxConfiguration = (TaxConfiguration) taxAppObjectMapper.convertJsonToObject(reqBody.get("taxConfiguration").trim(), TaxConfiguration.class);
        MerchantStore merchantStore = (MerchantStore) taxAppObjectMapper.convertJsonToObject(reqBody.get("merchantStore").trim(), MerchantStore.class);
        if(taxConfiguration != null && merchantStore != null) {
            MerchantConfiguration returnValue  = taxAppService.saveTaxConfiguration(taxConfiguration, merchantStore);
            if(returnValue == null) {
                return "FAILED";
            }
        }
        return "SUCCESS";
    }

}
