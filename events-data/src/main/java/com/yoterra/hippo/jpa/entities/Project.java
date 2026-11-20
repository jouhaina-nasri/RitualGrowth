package com.yoterra.hippo.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "ProjectWebsite")
    private String projectWebsite;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="project")
    private List<Cohort> cohort;

    public Project(String projectName,String projectWebsite) {
        this.projectName = projectName;
        this.projectWebsite = projectWebsite;
    }
}
