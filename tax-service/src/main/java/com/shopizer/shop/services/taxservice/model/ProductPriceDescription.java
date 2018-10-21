package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT_PRICE_DESCRIPTION", schema = TaxServiceConstants.DB_SCHEMA_NAME, uniqueConstraints={
        @UniqueConstraint(columnNames={
                "PRODUCT_PRICE_ID",
                "LANGUAGE_ID"
        })
}
)
public class ProductPriceDescription extends Description {
    private static final long serialVersionUID = 270521409645392808L;

    public final static String DEFAULT_PRICE_DESCRIPTION = "DEFAULT";

    @ManyToOne(targetEntity = ProductPrice.class)
    @JoinColumn(name = "PRODUCT_PRICE_ID", nullable = false)
    private ProductPrice productPrice;

    public ProductPriceDescription() {
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(ProductPrice productPrice) {
        this.productPrice = productPrice;
    }


}

