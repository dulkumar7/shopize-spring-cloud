package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT_OPTION_DESC", schema = TaxServiceConstants.DB_SCHEMA_NAME, uniqueConstraints={
        @UniqueConstraint(columnNames={
                "PRODUCT_OPTION_ID",
                "LANGUAGE_ID"
        })
}
)
public class ProductOptionDescription extends Description {
    private static final long serialVersionUID = -3158504904707188465L;

    @ManyToOne(targetEntity = ProductOption.class)
    @JoinColumn(name = "PRODUCT_OPTION_ID", nullable = false)
    private ProductOption productOption;

    @Column(name="PRODUCT_OPTION_COMMENT")
    private String productOptionComment;

    public ProductOptionDescription() {
    }

    public String getProductOptionComment() {
        return productOptionComment;
    }
    public void setProductOptionComment(String productOptionComment) {
        this.productOptionComment = productOptionComment;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }
}
