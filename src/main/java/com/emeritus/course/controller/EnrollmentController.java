package com.emeritus.course.controller;

import com.emeritus.course.model.Course;
import com.emeritus.course.model.EmeritusUser;
import com.emeritus.course.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping(path = "/enroll")
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping(path = "/course/{courseTitle}")
    @Secured({"STUDENT"})
    public EmeritusUser enrollInCourse(Principal principal, @PathVariable("courseTitle") String courseTitle){
        return enrollmentService.enrollInCourse(principal.getName(), courseTitle);
    }

    @PostMapping(path = "/course/{courseTitle}/assignment/{assignmentTitle}")
    @Secured({"INSTRUCTOR"})
    public Course enrollAssignmentInCourse(Principal principal, @PathVariable("courseTitle") String courseTitle,
                                           @PathVariable("assignmentTitle") String assignmentTitle){
        return enrollmentService.enrollAssignmentInCourse(principal.getName(), courseTitle, assignmentTitle);
    }

    @PostMapping(path = "/assignment/{assignmentTitle}")
    @Secured({"STUDENT"})
    public EmeritusUser enrollInAssignment(Principal principal, @PathVariable("assignmentTitle") String assignmentTitle){
        return enrollmentService.enrollInAssignment(principal.getName(), assignmentTitle);
    }
}
