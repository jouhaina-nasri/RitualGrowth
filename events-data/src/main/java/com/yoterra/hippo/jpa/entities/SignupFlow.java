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
@Table(name = "SignupFlow")
public class SignupFlow extends StageStats {

    @Column(name = "percentCompleted")
    private double percentCompleted;

    @Column(name = " percentNotCompleted")
    private double percentNotCompleted;

    @Column(name = "stoppedScreenOne")
    private long stoppedScreenOne;

    @Column(name = "stoppedScreenTwo")
    private long stoppedScreenTwo;

    @Column(name = "stoppedScreenThree")
    private long stoppedScreenThree;

    @Column(name = "stoppedScreenFour")
    private long stoppedScreenFour;
}
