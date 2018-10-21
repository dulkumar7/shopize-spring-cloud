package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="CUSTOMER_OPTION_DESC", schema = TaxServiceConstants.DB_SCHEMA_NAME, uniqueConstraints={
        @UniqueConstraint(columnNames={
                "CUSTOMER_OPTION_ID",
                "LANGUAGE_ID"
        })
}
)
public class CustomerOptionDescription extends Description {
    private static final long serialVersionUID = -3158504904707188465L;

    @ManyToOne(targetEntity = CustomerOption.class)
    @JoinColumn(name = "CUSTOMER_OPTION_ID", nullable = false)
    private CustomerOption customerOption;

    @Column(name="CUSTOMER_OPTION_COMMENT")
    private String customerOptionComment;



    public CustomerOptionDescription() {
    }

    public CustomerOption getCustomerOption() {
        return customerOption;
    }

    public void setCustomerOption(CustomerOption customerOption) {
        this.customerOption = customerOption;
    }

    public String getCustomerOptionComment() {
        return customerOptionComment;
    }

    public void setCustomerOptionComment(String customerOptionComment) {
        this.customerOptionComment = customerOptionComment;
    }
}
