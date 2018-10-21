package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_OPTION_VALUE_DESCRIPTION", schema = TaxServiceConstants.DB_SCHEMA_NAME, uniqueConstraints={
        @UniqueConstraint(columnNames={
                "PRODUCT_OPTION_VALUE_ID",
                "LANGUAGE_ID"
        })
}
)
public class ProductOptionValueDescription extends Description {
    private static final long serialVersionUID = 7402155175956813576L;

    @ManyToOne(targetEntity = ProductOptionValue.class)
    @JoinColumn(name = "PRODUCT_OPTION_VALUE_ID")
    private ProductOptionValue productOptionValue;

    public ProductOptionValueDescription() {
    }

    public ProductOptionValue getProductOptionValue() {
        return productOptionValue;
    }

    public void setProductOptionValue(ProductOptionValue productOptionValue) {
        this.productOptionValue = productOptionValue;
    }

}
