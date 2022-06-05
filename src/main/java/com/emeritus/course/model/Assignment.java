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
public class Assignment implements Serializable {

    @Id
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTs;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTs;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_assignment",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courseAssignment;

    @ManyToMany(mappedBy = "assignmentEnrollment", fetch = FetchType.EAGER)
    private Set<EmeritusUser> emeritusUsers;

}
