package com.yoterra.hippo.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Engagement")
public class Engagement extends StageStats {


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "engagement")
    private List<EngagementData> engagementData;

    @Column(name = "userEngagement")
    public double userEngagement;

}
