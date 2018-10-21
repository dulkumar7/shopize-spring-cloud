package com.shopizer.shop.services.taxservice.model;

import javax.persistence.*;

@Entity
@Table(name="ZONE_DESCRIPTION", uniqueConstraints={
        @UniqueConstraint(columnNames = {
                "ZONE_ID",
                "LANGUAGE_ID"
        })})
public class ZoneDescription extends Description {
    private static final long serialVersionUID = 6448836326562270923L;

    @ManyToOne(targetEntity = Zone.class)
    @JoinColumn(name = "ZONE_ID", nullable = false)
    private Zone zone;

    public ZoneDescription() {
    }

    public ZoneDescription(Zone zone, Language language, String name) {
        setZone(zone);
        setLanguage(language);
        setName(name);
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}

