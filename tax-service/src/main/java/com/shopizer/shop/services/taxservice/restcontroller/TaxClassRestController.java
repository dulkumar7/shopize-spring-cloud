package com.shopizer.shop.services.taxservice.restcontroller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.shopizer.shop.services.taxservice.mapper.TaxAppObjectMapper;
import com.shopizer.shop.services.taxservice.model.*;
import com.shopizer.shop.services.taxservice.service.TaxAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/tax-service/tax-class")
public class TaxClassRestController {

    private Logger logger = LoggerFactory.getLogger("TaxClassRestController");

    @Autowired
    private TaxAppService taxAppService;

    @Autowired
    private TaxAppObjectMapper taxAppObjectMapper;

    @GetMapping("/list-partial/{storeId}")
    @HystrixCommand(fallbackMethod = "retrieveClassListFallBack")
    public String retrieveClassList(@PathVariable Integer storeId) {
        logger.info("START: TaxClassRestController.retrieveClassList() -- input = ", storeId);
        AjaxResponse resp = new AjaxResponse();
        try {
            List<TaxClass> taxClasses = taxAppService.listTaxClassByStore(storeId);
            for(TaxClass tax : taxClasses) {
                if(!tax.getCode().equals(TaxClass.DEFAULT_TAX_CLASS)) {
                    Map<String,String> entry = new HashMap<String,String>();
                    entry.put("taxClassId", String.valueOf(tax.getId()));
                    entry.put("code", tax.getCode());
                    entry.put("name", tax.getTitle());
                    resp.addDataEntry(entry);
                }
            }
            resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_SUCCESS);
        } catch (Exception e) {
            logger.error("Error while paging permissions", e);
            resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
            resp.setErrorMessage(e);
        }

        String returnString = resp.toJSONString();
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        logger.info("End: TaxClassRestController.retrieveClassList() -- output = ", returnString);
        return returnString;
    }

    /**
     * FallBack method for TaxClassRestController.retrieveClassList()
     * @param storeId
     * @return
     */
    public String retrieveClassListFallBack(Integer storeId) {
        return new AjaxResponse().toJSONString();
    }

    @GetMapping("/list-full/{storeId}")
    @HystrixCommand(fallbackMethod = "retrieveTaxClassListFullFallBack")
    public String retrieveTaxClassListFull(@PathVariable Integer storeId) {
        logger.info("START: TaxClassRestController.retrieveTaxClassListFull() -- input = ", storeId);
        List<TaxClass> taxClasses = taxAppService.listTaxClassByStore(storeId);
        String result = taxAppObjectMapper.convertObjectToString(taxClasses);
        logger.info("START: TaxClassRestController.retrieveTaxClassListFull() -- output = ", result);
        return result;
    }

    /**
     * FallBack for retrieveTaxClassListFull
     * @return String
     */
    public String retrieveTaxClassListFullFallBack(Integer storeId) {
        List<TaxClass> taxclassList = new ArrayList<>();
        return taxAppObjectMapper.convertObjectToString(taxclassList);
    }

    @GetMapping("/storeId/{storeId}/tax-code/{code}")
    public TaxClass retrieveTaxClassByCode(@PathVariable Integer storeId, @PathVariable String code) {
    	logger.info("START: TaxClassRestController.retrieveTaxClassByCode() -- input = ",storeId, code);
        TaxClass result = taxAppService.getTaxClassByStoreAndTaxCode(storeId, code);
        logger.info("END: TaxClassRestController.retrieveTaxClassByCode() -- output = ", result);
        return result;
        
    }

    @PostMapping("/create")
    public TaxClass createTaxClass(@RequestBody Map<String, String> reqBody) {
    	logger.info("START: TaxClassRestController.createTaxClass() -- input = ", reqBody);
        TaxClass taxClassOut = null;
        try {
            TaxClass taxClassIn = (TaxClass) taxAppObjectMapper.convertJsonToObject(reqBody.get("taxClass"), TaxClass.class);
            MerchantStore storeIn = (MerchantStore) taxAppObjectMapper.convertJsonToObject(reqBody.get("merchantStore").trim(), MerchantStore.class);
            if(taxClassIn != null) {
                taxClassIn.setMerchantStore(storeIn);
                taxClassOut =  taxAppService.createAndUpdateTaxClass(taxClassIn);
            }
        } catch(Exception ex) {
            logger.error("Failed to create TaxClass ", ex);
        }
        logger.info("END: TaxClassRestController.createTaxClass() -- output = ", taxClassOut);
        return taxClassOut;
    }

    @PutMapping("/update")
    @HystrixCommand(fallbackMethod = "updateClassFallBack")
    public String updateClass(@RequestBody Map<String, String> reqBody) {
    	logger.info("START: TaxClassRestController.updateClass() -- input = ", reqBody);
        TaxClass taxClassOut = null;
        try {
            TaxClass taxClassIn = (TaxClass) taxAppObjectMapper.convertJsonToObject(reqBody.get("taxClass"), TaxClass.class);
            MerchantStore storeIn = (MerchantStore) taxAppObjectMapper.convertJsonToObject(reqBody.get("merchantStore").trim(), MerchantStore.class);
            if(taxClassIn != null) {
                taxClassIn.setMerchantStore(storeIn);
                taxClassOut = taxAppService.createAndUpdateTaxClass(taxClassIn);
            }
        } catch(Exception ex) {
            logger.error("Failed to create TaxClass ", ex);
        }
        logger.info("END: TaxClassRestController.updateClass() -- output = ", taxClassOut);
        return "UPDATE SUCCESS";
    }

    /**
     * FallBack method of the endpoint above
     * @return String
     */
    public String updateClassFallBack(Map<String, String> reqBody) {
        return "UPDATE FAILED";
    }

    @GetMapping("/id/{taxClassId}")
    @HystrixCommand(fallbackMethod = "retrieveTaxClassByIdFallBack")
    public String retrieveTaxClassById(@PathVariable Long taxClassId) {
    	logger.info("START: TaxClassRestController.retrieveTaxClassById() -- input = ", taxClassId);
        TaxClass taxResp = taxAppService.getTaxClassById(taxClassId);
        String returnValue = taxAppObjectMapper.convertObjectToString(taxResp);
        logger.info("END: TaxClassRestController.retrieveTaxClassById() -- output = ", returnValue);
        return returnValue;
    }

    /**
     * FallBack method for retrieveTaxClassById
     * @return String
     */
    public String retrieveTaxClassByIdFallBack(Long taxClassId) {
        return taxAppObjectMapper.convertObjectToString(new TaxClass());
    }

    @GetMapping("/products/id/{taxClassId}")
    public List<Product> listProductsByTaxClass(@PathVariable Long taxClassId) {
    	logger.info("START: TaxClassRestController.listProductsByTaxClass() -- input = ", taxClassId);
        List<Product> productList = taxAppService.listProductsByTaxClass(taxClassId);
        logger.info("END: TaxClassRestController.listProductsByTaxClass() -- output = ", productList);
        return productList;
    }

    @DeleteMapping("/delete/{taxClassId}")
    public void removeTaxClass(@PathVariable Long taxClassId) {
    	logger.info("START: TaxClassRestController.removeTaxClass() -- input = ", taxClassId);
        taxAppService.deleteTaxClass(taxClassId);
        logger.info("END: TaxClassRestController.removeTaxClass() -- output = void");
    }

}
