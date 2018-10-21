package com.shopizer.shop.services.taxservice.restcontroller;

import com.shopizer.shop.services.taxservice.mapper.TaxAppObjectMapper;
import com.shopizer.shop.services.taxservice.model.*;
import com.shopizer.shop.services.taxservice.service.TaxAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/tax-service/tax-rates")
public class TaxRatesRestController {

    private Logger logger = LoggerFactory.getLogger(TaxRatesRestController.class);

    @Autowired
    private TaxAppService taxAppService;

    @Autowired
    private TaxAppObjectMapper taxAppObjectMapper;

    @GetMapping("/list-by-store/{storeId}")
    public String retrieveTaxRatesByStoreId(@PathVariable Integer storeId) {
        logger.info("START: TaxRatesRestController.retrieveTaxRatesByStoreId() -- input = ", storeId);
        List<TaxRate> taxRateList = taxAppService.listTaxRatesByStore(storeId);
        String result = "";
        if(taxRateList != null) {
            result = taxAppObjectMapper.convertObjectToString(taxRateList);
        }
        logger.info("END: TaxRatesRestController.retrieveTaxRatesByStoreId() -- output = ", result);
        return result;
    }

    @PostMapping("/create")
    public TaxRate createTaxRate(@RequestBody Map<String, String> reqBody) {
        logger.info("START: TaxRatesRestController.createTaxRate() -- input = ", reqBody);
        TaxRate taxRateOut = null;
        try {
            TaxRate taxRateIn = rebuildTaxRateObject(reqBody);
            if(taxRateIn != null) {
                taxRateOut =  taxAppService.createAndUpdateTaxRate(taxRateIn);
            }
        } catch(Exception ex) {
            logger.error("Failed to create TaxRate ", ex);
        }
        logger.info("END: TaxRatesRestController.createTaxRate() -- output = ", taxRateOut);
        return taxRateOut;
    }

    private TaxRate rebuildTaxRateObject(Map<String, String> mapper) {
        TaxRate taxRateIn = (TaxRate) taxAppObjectMapper.convertJsonToObject(mapper.get("taxRate"), TaxRate.class);
        MerchantStore storeIn = (MerchantStore) taxAppObjectMapper.convertJsonToObject(mapper.get("merchantStore").trim(), MerchantStore.class);
        Country country = (Country) taxAppObjectMapper.convertJsonToObject(mapper.get("country"), Country.class);
        List<TaxRateDescription> taxRateDesc = new ArrayList<>();
        List<?> taxDescJson = (List<?>) taxAppObjectMapper.convertJsonToObject(mapper.get("taxRateDescriptions"), List.class);
        taxDescJson.forEach(data -> {
            TaxRateDescription taxRateDescription = (TaxRateDescription)taxAppObjectMapper.convertJsonToObject(taxAppObjectMapper.convertObjectToString(data), TaxRateDescription.class);
            if(taxRateDescription.getTaxRate() == null) {
                taxRateDescription.setTaxRate(taxRateIn);
            }
            taxRateDesc.add(taxRateDescription);
        });
        Zone zone = (Zone) taxAppObjectMapper.convertJsonToObject(mapper.get("zone"), Zone.class);
        TaxRate parent = (TaxRate) taxAppObjectMapper.convertJsonToObject(mapper.get("parent"), TaxRate.class);
        TaxClass taxClass = (TaxClass) taxAppObjectMapper.convertJsonToObject(mapper.get("taxClass"), TaxClass.class);
        taxRateIn.setMerchantStore(storeIn);
        taxRateIn.setCountry(country);
        taxRateIn.setDescriptions(taxRateDesc);
        taxRateIn.setZone(zone);
        taxRateIn.setParent(parent);
        taxRateIn.setTaxClass(taxClass);
        return taxRateIn;
    }


}
