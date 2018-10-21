package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_REVIEW_DESCRIPTION", schema = TaxServiceConstants.DB_SCHEMA_NAME,
        uniqueConstraints={
        @UniqueConstraint(columnNames={
                "PRODUCT_REVIEW_ID",
                "LANGUAGE_ID"
        })
})
public class ProductReviewDescription extends Description {
    private static final long serialVersionUID = -1957502640742695406L;

    @ManyToOne(targetEntity = ProductReview.class)
    @JoinColumn(name="PRODUCT_REVIEW_ID")
    private ProductReview productReview;

    public ProductReviewDescription() {
    }

    public ProductReviewDescription(Language language, String name) {
        this.setLanguage(language);
        this.setName(name);
    }

    public ProductReview getProductReview() {
        return productReview;
    }

    public void setProductReview(ProductReview productReview) {
        this.productReview = productReview;
    }
}

