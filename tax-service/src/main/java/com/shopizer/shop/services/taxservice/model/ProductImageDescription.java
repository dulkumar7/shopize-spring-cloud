package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;

import javax.persistence.*;

@Entity
@Table(name="PRODUCT_IMAGE_DESCRIPTION", schema = TaxServiceConstants.DB_SCHEMA_NAME, uniqueConstraints={
        @UniqueConstraint(columnNames={
                "PRODUCT_IMAGE_ID",
                "LANGUAGE_ID"
        })
}
)
public class ProductImageDescription extends Description {
    private static final long serialVersionUID = 247514890386076337L;

    @ManyToOne(targetEntity = ProductImage.class)
    @JoinColumn(name = "PRODUCT_IMAGE_ID", nullable = false)
    private ProductImage productImage;

    @Column(name="ALT_TAG", length=100)
    private String altTag;

    public ProductImage getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImage productImage) {
        this.productImage = productImage;
    }

    public String getAltTag() {
        return altTag;
    }

    public void setAltTag(String altTag) {
        this.altTag = altTag;
    }


}