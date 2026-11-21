package com.yoterra.hippo.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "EngagementData")
public class EngagementData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "cohort_id")
    private long cohortID;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "differentPage")
    private long differentPage;

    @Column(name = "samePage")
    private long samePage;

    @Column(name = "numberComment")
    private long numberComment;

    @Column(name = "numberLike")
    private long numberLike;

    @Column(name = "numberFollowers")
    private long numberFollowers;

    @Column(name = "numberCollection")
    private long numberCollection;

    @Column(name = "numberStores")
    private long numberStores;

    @Column(name = "numberBrands")
    private long numberBrands;

    @Column(name = "directMessages")
    private long directMessages;

    @Column(name = "numberProduct")
    private long numberProduct;

    @Column(name = "userEngagement")
    private double userEngagement;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "engagement_id")
    private Engagement engagement;
}
