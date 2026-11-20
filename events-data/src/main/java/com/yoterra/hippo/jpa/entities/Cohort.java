package com.yoterra.hippo.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoterra.hippo.jpa.entities.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Cohort")
public class Cohort implements Serializable {
    public Cohort() {
        this.version = "";
    }

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    private long id;

    @Column(name = "CohortName")
    private String cohortName;

    @Column(name = "StartDate")
    private Timestamp startDate;

    @Column(name = "EndDate")
    private Timestamp endDate;

    @Column(name = "Version")
    private String version;

    @Column(name = "project_id")
    private Long project_id;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "cohort_user",
            joinColumns = { @JoinColumn(name = "cohort_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, mappedBy="cohort")
    private List<Stage> stages;

    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Project.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private Project project;


}
