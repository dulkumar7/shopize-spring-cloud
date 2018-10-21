package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_OPT_VAL_DESCRIPTION", schema = TaxServiceConstants.DB_SCHEMA_NAME, uniqueConstraints={
        @UniqueConstraint(columnNames={
                "CUSTOMER_OPT_VAL_ID",
                "LANGUAGE_ID"
        })
})
public class CustomerOptionValueDescription extends Description {
    private static final long serialVersionUID = 7402155175956813576L;

    @ManyToOne(targetEntity = CustomerOptionValue.class)
    @JoinColumn(name = "CUSTOMER_OPT_VAL_ID")
    private CustomerOptionValue customerOptionValue;


    public CustomerOptionValueDescription() {
    }

    public CustomerOptionValue getCustomerOptionValue() {
        return customerOptionValue;
    }

    public void setCustomerOptionValue(CustomerOptionValue customerOptionValue) {
        this.customerOptionValue = customerOptionValue;
    }

}

