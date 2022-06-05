package com.emeritus.course.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Course implements Serializable {

    @Id
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTs;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTs;

    @ManyToMany(mappedBy = "courseEnrollment", fetch = FetchType.EAGER)
    private Set<EmeritusUser> emeritusUsers;

    @ManyToMany(mappedBy = "courseAssignment", fetch = FetchType.EAGER)
    private Set<Assignment> assignments;
}
