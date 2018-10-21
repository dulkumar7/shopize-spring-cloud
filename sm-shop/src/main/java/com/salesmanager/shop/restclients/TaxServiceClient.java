package com.salesmanager.shop.restclients;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.tax.taxrate.TaxRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.tax.TaxConfiguration;
import com.salesmanager.core.model.tax.taxclass.TaxClass;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class TaxServiceClient {

    private static Logger logger = LoggerFactory.getLogger(TaxServiceClient.class);

//    @Value("${TAX_SERVICE_URL}")
    private String taxServiceUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public URI getTaxServiceUrl(String endpoint) {
        return UriComponentsBuilder.fromUriString(taxServiceUrl + endpoint).build().toUri();
        //return loadBalancerClient.choose(taxServiceUrl).getUri();
    }

    public TaxConfiguration getTaxConfiguration(MerchantStore store) {
    	logger.info("START: TaxServiceClient.getTaxConfiguration()");
    	final URI uri = getTaxServiceUrl("/rest/tax-service/tax-configuration/edit");
        logger.info("service-endpoint: ", uri);
        TaxConfiguration response = restTemplate.postForObject(uri, store.getId(), TaxConfiguration.class);
        logger.info("END: TaxServiceClient.getTaxConfiguration() -- return value = ", response);
        return response;
    }

    public void saveTaxConfiguration(TaxConfiguration taxConfiguration, MerchantStore merchantStore) {
    	logger.info("START: TaxServiceClient.createTaxConfiguration()");
        Map<String, Object> reqBody = new HashMap<>();
        try {
            reqBody.put("taxConfiguration", objectMapper.writeValueAsString(taxConfiguration));
            reqBody.put("merchantStore", objectMapper.writeValueAsString(merchantStore));
        } catch(IOException ioe) {
            logger.error("Exception while parsing ", ioe);
        }
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-configuration/save");
        logger.info("service-endpoint: ", uri);
        String result = restTemplate.postForObject(uri, reqBody, String.class);
        logger.info("END: TaxServiceClient.saveTaxConfiguration() -- return value = ",result);
    }

    public String getTaxClassPartialList(MerchantStore store) {
        logger.info("START: TaxServiceClient.getTaxClassPartialList()");
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-class/list-partial/" + store.getId());
        logger.info("service-endpoint: ", uri);
        String returnValue = restTemplate.getForObject(uri, String.class);
        logger.info("END: TaxServiceClient.getTaxClassPartialList() -- return value = ", returnValue);
        return returnValue;
    }

    public List<TaxClass> getTaxClassFullList(MerchantStore store) {
        logger.info("START: TaxServiceClient.getTaxClassFullList()");
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-class/list-full/" + store.getId());
        logger.info("service-endpoint: ", uri);
        String returnValue = restTemplate.getForObject(uri, String.class);
        List<TaxClass> taxClassList = new ArrayList<>();
        try {
        	List<Object> taxClassJsonList = (List<Object>) objectMapper.readValue(returnValue, List.class);
        	//convert json strings to taxclass objects
        	taxClassJsonList.forEach(data -> {
                try {
                    String jsonStr = objectMapper.writeValueAsString(data);
                    taxClassList.add(objectMapper.readValue(jsonStr, TaxClass.class));
                } catch (IOException e) {
                    logger.error("Error while parsiing TaxClass ", e);
                }

            });
        } catch (IOException ioe) {
            logger.error("Exception while parsing list of TaxClass", ioe);
        }
        logger.info("END: TaxServiceClient.getTaxClassFullList() -- return value = ", returnValue);
        return taxClassList;
    }

    public TaxClass getTaxClassByStoreAndCode(Integer storeId, String taxCode) {
        logger.info("START: TaxServiceClient.getTaxClassByStoreAndCode()");
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-class/storeId/" + storeId + "/tax-code/" + taxCode);
        logger.info("service-endpoint: ", uri);
        TaxClass response = restTemplate.getForObject(uri, TaxClass.class);
        logger.info("END: TaxServiceClient.getTaxClassByStoreAndCode() -- return value = ", response);
        return response;
    }

    public TaxClass createTaxClass(TaxClass taxClass, MerchantStore store) {
        logger.info("START: TaxServiceClient.createTaxClass()");
        Map<String, String> reqBody = new HashMap<>();
        try {
            reqBody.put("taxClass", objectMapper.writeValueAsString(taxClass));
            reqBody.put("merchantStore", objectMapper.writeValueAsString(store));
        }catch(IOException ioe) {
            logger.error("Exception while parsing ", ioe);
        }
        logger.info("Request body: ", reqBody);
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-class/create");
        logger.info("service-endpoint: ", uri);
        TaxClass response = restTemplate.postForObject(uri, reqBody, TaxClass.class);
        logger.info("END: TaxServiceClient.createTaxClass() -- return value = ", response);
        return response;
    }

    public void updateTaxClass(TaxClass taxClass, MerchantStore store) {
        logger.info("START: TaxServiceClient.updateTaxClass()");
        Map<String, String> reqBody = new HashMap<>();
        try{
            reqBody.put("taxClass", objectMapper.writeValueAsString(taxClass));
            reqBody.put("merchantStore", objectMapper.writeValueAsString(store));
        } catch(IOException ioe) {
            logger.error("Exception while parsion ", ioe);
        }
        logger.info("Request body: ", reqBody);
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-class/update");
        logger.info("service-endpoint: ", uri);
        restTemplate.put(uri, reqBody);
        logger.info("END: TaxServiceClient.updateTaxClass()");
    }

    public TaxClass getTaxClassById(long taxClassId) {
        logger.info("START: TaxServiceClient.getTaxClassById()");
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-class/id/" + taxClassId);
        logger.info("service-endpoint: ", uri);
        String response = restTemplate.getForObject(uri, String.class);
        TaxClass taxClass = null;
        try {
            taxClass = objectMapper.readValue(response, TaxClass.class);
        } catch (IOException e) {
            logger.error("Error while parsin taxClass ", e);
        }
        logger.info("END: TaxServiceClient.getTaxClassById()", taxClass);
        return taxClass;
    }

    public List<Product> listProductsByTaxClass(TaxClass taxClass) {
        logger.info("START: TaxServiceClient.listProductsByTaxClass()");
        final URI uri =  getTaxServiceUrl("/rest/tax-service/tax-class/products/id/" + taxClass.getId()) ;
        logger.info("service-endpoint: ", uri);
        List<Product> products = restTemplate.getForObject(uri, List.class);
        logger.info("END: TaxServiceClient.listProductsByTaxClass()", products);
        return products;
    }

    public void deleteTaxClass(TaxClass taxClass) {
        logger.info("START: TaxServiceClient.deleteTaxClass()");
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-class/delete/" + taxClass.getId());
        logger.info("service-endpoint: ", uri);
        restTemplate.delete(uri);
        logger.info("END: TaxServiceClient.deleteTaxClass()");
    }

    public List<TaxRate> listTaxRatesByStore(MerchantStore store) {
        logger.info("START: TaxServiceClient.listTaxRatesByStore() -- input = ", store);
        List<TaxRate> result = new ArrayList<>();
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-rates/list-by-store/" + store.getId());
        logger.info("service-endpoint: ", uri);
        String taxRatesListStr = restTemplate.getForObject(uri, String.class);
        try {
            result = (List<TaxRate>)objectMapper.readValue(taxRatesListStr, List.class);
        } catch (IOException ioe) {
            logger.error("Parsing exception ", ioe);
        }
        logger.info("END: TaxServiceClient.listTaxRatesByStore() -- output = ", result);
        return result;
    }

    public TaxRate createTaxRate(TaxRate taxRate, MerchantStore store) {
        logger.info("START: TaxServiceClient.createTaxRate() -- input = ", taxRate);
        final URI uri = getTaxServiceUrl("/rest/tax-service/tax-rates/create");
        Map<String, String> reqBody = new HashMap<>();
        try {
            reqBody.put("taxRate", objectMapper.writeValueAsString(taxRate));
            reqBody.put("merchantStore", objectMapper.writeValueAsString(store));
            reqBody.put("country", objectMapper.writeValueAsString(taxRate.getCountry()));
            reqBody.put("taxRateDescriptions", objectMapper.writeValueAsString(taxRate.getDescriptions()));
            reqBody.put("zone", objectMapper.writeValueAsString(taxRate.getZone()));
            reqBody.put("parent", objectMapper.writeValueAsString(taxRate.getParent()));
            reqBody.put("taxClass", objectMapper.writeValueAsString(taxRate.getTaxClass()));
        } catch(IOException ioe) {
            logger.error("Exception while parsing ", ioe);
        }
        logger.info("createTaxRate Request body: ", reqBody);
        logger.info("service-endpoint: ", uri);
        TaxRate response = restTemplate.postForObject(uri, reqBody, TaxRate.class);
        logger.info("END: TaxServiceClient.createTaxRate() -- return value = ", response);
        return response;
    }

}
