package com.yoterra.hippo.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ViralReferrals")
public class ViralReferrals extends StageStats {

    @Column(name = "averageInvites")
    private double averageInvites;
}
