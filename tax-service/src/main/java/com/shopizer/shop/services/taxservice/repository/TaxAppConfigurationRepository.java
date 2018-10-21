package com.shopizer.shop.services.taxservice.repository;

import com.shopizer.shop.services.taxservice.model.TaxConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxAppConfigurationRepository extends JpaRepository<TaxConfigEntity, Integer> {

}
