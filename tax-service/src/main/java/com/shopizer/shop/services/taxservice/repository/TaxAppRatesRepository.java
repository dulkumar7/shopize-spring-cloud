package com.shopizer.shop.services.taxservice.repository;

import com.shopizer.shop.services.taxservice.model.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaxAppRatesRepository extends JpaRepository<TaxRate, Long> {

    @Query("select t from TaxRate t join fetch t.taxClass join fetch t.merchantStore tm join fetch t.country left join fetch t.zone left join fetch t.descriptions left join t.parent where tm.id=?1 order by t.taxPriority asc")
    List<TaxRate> findByStoreId(Integer storeId);

}
