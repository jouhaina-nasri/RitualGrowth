package com.yoterra.hippo.jpa.entities;

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
@Table(name = "Monetization")
public class Monetization extends StageStats {

    @Column(name = "productClicked")
    private long productClicked; /* the percentage of users that by the product they clicked */

    @Column(name = "productDifferent")
    private long productDifferent; /* the percentage of users that buy a different product at the store */

    @Column(name = "productSales")
    private long productSales; /* the percentage of users buy the product */

    @Column(name = "moreProduct")
    private long moreProduct; /* the percentage of users that buy more product */

}
