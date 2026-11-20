package com.yoterra.hippo.jpa.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.sql.Timestamp;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class RetentionPeriod extends StageStats {

    @Column(name = "startDate")
    private Timestamp startDate;

    @Column(name = "endDate")
    private Timestamp endDate;
}
