package com.yoterra.hippo.jpa.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class StageStats implements Serializable {
    private static final long serialVersionUID = -695503064509648117L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cohort_id")
    private long cohortID;

    @Column(name = "entered")
    private long peopleEntered;

    @Column(name = "inactive")
    private long peopleInactive;

    @Column(name = "exit")
    private long peopleExit;

    @Column(name = "overall")
    private long overall;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="stageStats")
    public List<Stage> stages;

}
