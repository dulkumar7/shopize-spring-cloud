package com.shopizer.shop.services.taxservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;

import javax.persistence.*;

@Entity
@Table(name = "TAX_RATE_DESCRIPTION" , schema = TaxServiceConstants.DB_SCHEMA_NAME, uniqueConstraints={
        @UniqueConstraint(columnNames={
                "TAX_RATE_ID",
                "LANGUAGE_ID"
        })
}
)
public class TaxRateDescription extends Description {
    private static final long serialVersionUID = -4752794805878361822L;

    @ManyToOne(targetEntity = TaxRate.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "TAX_RATE_ID")
    @JsonBackReference("taxRate")
    private TaxRate taxRate;

    public TaxRateDescription() {
    }

    public TaxRate getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxRate taxRate) {
        this.taxRate = taxRate;
    }
}
