package com.shopizer.shop.services.taxservice.repository;

import com.shopizer.shop.services.taxservice.model.MerchantConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaxMerchantConfigRepository extends JpaRepository<MerchantConfiguration, Long> {

    /*@Query("select merchantConfigValue from TaxConfigEntity where taxConfigKey=:taxConfigKey and storeId=:storeId")
    public String getTaxConfigurationForMerchant(@Param("taxConfigKey") String taxConfigKey, @Param("storeId") String storeId);

    @Query("select m from MerchantConfiguration m join fetch m.merchantStore ms where ms.id=?1")
    List<MerchantConfiguration> findByMerchantStore(Integer id);*/

    @Query("select m from MerchantConfiguration m join fetch m.merchantStore ms where ms.id=?1 and m.key=?2")
    MerchantConfiguration findMerchantConfiguration(Integer id, String key);

    /*@Query("select m from MerchantConfiguration m join fetch m.merchantStore ms where ms.id=?1 and m.merchantConfigurationType=?2")
    List<MerchantConfiguration> findByMerchantStoreAndType(Integer id, MerchantConfigurationType type);*/
}
