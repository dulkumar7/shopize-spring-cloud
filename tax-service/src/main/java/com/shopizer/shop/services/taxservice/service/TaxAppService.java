package com.shopizer.shop.services.taxservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.shopizer.shop.services.taxservice.model.*;
import com.shopizer.shop.services.taxservice.repository.TaxAppClassRepository;
import com.shopizer.shop.services.taxservice.repository.TaxAppConfigurationRepository;
import com.shopizer.shop.services.taxservice.repository.TaxAppRatesRepository;
import com.shopizer.shop.services.taxservice.repository.TaxMerchantConfigRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxAppService {

    private final static String TAX_CONFIGURATION = "TAX_CONFIG";

    @Autowired
    private TaxAppConfigurationRepository taxConfigurationRepository;

    @Autowired
    private TaxMerchantConfigRepository taxMerchantConfigRepository;

    @Autowired
    private TaxAppClassRepository taxAppClassRepository;

    @Autowired
    private TaxAppRatesRepository taxAppRatesRepository;

    public TaxConfiguration getTaxConfiguration(Integer storeId) throws ServiceException {
        MerchantConfiguration merchantConfiguration = null;
        TaxConfiguration taxConfiguration = null;
        try {
            merchantConfiguration = taxMerchantConfigRepository.findMerchantConfiguration(storeId, TAX_CONFIGURATION);
            if(merchantConfiguration != null) {
                ObjectMapper mapper = new ObjectMapper();
                taxConfiguration = mapper.readValue(merchantConfiguration.getValue(), TaxConfiguration.class);
            }

        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        } catch (Exception ex) {
            throw new ServiceException("Cannot parse json string " + merchantConfiguration.getValue());
        }
        return taxConfiguration;
    }


    public MerchantConfiguration saveTaxConfiguration(TaxConfiguration shippingConfiguration, MerchantStore store) throws ServiceException {

        MerchantConfiguration merchantConfiguration = taxMerchantConfigRepository.findMerchantConfiguration(store.getId(), TAX_CONFIGURATION);

        if(merchantConfiguration == null) {
            merchantConfiguration = new MerchantConfiguration();
            merchantConfiguration.setMerchantStore(store);
            merchantConfiguration.setKey(TAX_CONFIGURATION); //mapped
        }

        String value = shippingConfiguration.toJSONString();
        merchantConfiguration.setValue(value); //maped

        MerchantConfiguration createdConfig = taxMerchantConfigRepository.saveAndFlush(merchantConfiguration);
        return createdConfig;

    }

    public List<TaxClass> listTaxClassByStore(Integer storeId) throws ServiceException {
        return taxAppClassRepository.findByStore(storeId);
    }

    public List<TaxRate> listTaxRatesByStore(Integer storeId) {
        return taxAppRatesRepository.findByStoreId(storeId);
    }

    public TaxClass getTaxClassByStoreAndTaxCode(Integer storeId, String taxCode) throws ServiceException {
        return taxAppClassRepository.findByStoreAndCode(storeId, taxCode);
    }

    public TaxClass createAndUpdateTaxClass(TaxClass taxClass) {
        return (TaxClass) taxAppClassRepository.saveAndFlush(taxClass);
    }

    public TaxClass getTaxClassById(Long taxClassId) {
        return taxAppClassRepository.getById(taxClassId);
    }

    public List<Product> listProductsByTaxClass(Long taxClassId) {
        return taxAppClassRepository.listProductsOfTaxClass(taxClassId);
    }

    public void deleteTaxClass(Long taxClassId) {
        taxAppClassRepository.delete(taxClassId);
    }

    public TaxRate createAndUpdateTaxRate(TaxRate taxRateIn) {
        return (TaxRate) taxAppRatesRepository.save(taxRateIn);
    }
}
