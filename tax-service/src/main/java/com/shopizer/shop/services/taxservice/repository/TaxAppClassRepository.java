package com.shopizer.shop.services.taxservice.repository;

import com.shopizer.shop.services.taxservice.model.Product;
import com.shopizer.shop.services.taxservice.model.TaxClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaxAppClassRepository extends JpaRepository<TaxClass, Long> {

    String productsByTaxQuery =  "select p from Product as p " + "join fetch p.merchantStore merch " +
		                                "join fetch p.availabilities pa " + "left join fetch pa.prices pap " +
                                        "join fetch p.descriptions pd " + "join fetch p.categories categs " +
                                        "left join fetch pap.descriptions papd " + "left join fetch p.images images " +
                                        "left join fetch p.attributes pattr " + "left join fetch pattr.productOption po " +
                                        "left join fetch po.descriptions pod " + "left join fetch pattr.productOptionValue pov " +
                                        "left join fetch pov.descriptions povd " + "left join fetch p.manufacturer manuf " +
                                        "left join fetch manuf.descriptions manufd " + "left join fetch p.type type " +
                                        "left join fetch p.taxClass tx " + "left join fetch p.owner owner " +
                                        "where tx.id=:taxClassId";

    @Query("select t from TaxClass t join fetch t.merchantStore tm where tm.id=?1")
    List<TaxClass> findByStore(Integer id);

    @Query("select t from TaxClass t join fetch t.merchantStore tm where t.code=?1")
    TaxClass findByCode(String code);

    @Query("select t from TaxClass t join fetch t.merchantStore tm where tm.id=?1 and t.code=?2")
    TaxClass findByStoreAndCode(Integer id, String code);

    TaxClass getById(Long taxClassId);

    @Query(productsByTaxQuery)
    List<Product> listProductsOfTaxClass(@Param("taxClassId") Long taxClassId);
}