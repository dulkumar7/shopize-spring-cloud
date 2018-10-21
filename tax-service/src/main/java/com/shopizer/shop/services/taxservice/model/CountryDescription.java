package com.shopizer.shop.services.taxservice.model;

import com.shopizer.shop.services.taxservice.constants.TaxServiceConstants;

import javax.persistence.*;

@Entity
@Table(name = "COUNTRY_DESCRIPTION", schema = TaxServiceConstants.DB_SCHEMA_NAME, uniqueConstraints={
        @UniqueConstraint( columnNames = {
                "COUNTRY_ID",
                "LANGUAGE_ID"
        })})
public class CountryDescription extends Description {
    private static final long serialVersionUID = 9048940117896071174L;

    @ManyToOne(targetEntity = Country.class)
    @JoinColumn(name = "COUNTRY_ID", nullable = false)
    private Country country;

    public CountryDescription() {
    }

    public CountryDescription(Language language, String name) {
        this.setLanguage(language);
        this.setName(name);
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}