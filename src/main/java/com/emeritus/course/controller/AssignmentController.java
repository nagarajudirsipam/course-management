package com.emeritus.course.controller;

import com.emeritus.course.model.Assignment;
import com.emeritus.course.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/assignment")
@Secured({"INSTRUCTOR"})
public class AssignmentController {
    @Autowired
    AssignmentService assignmentService;

    @GetMapping
    public Iterable<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @PostMapping("/{courseTitle}")
    public Assignment saveAssignment(@RequestBody Assignment assignment, @PathVariable("courseTitle") String courseTitle) {
        return assignmentService.saveAssignment(assignment);
    }

    @GetMapping("/{title}")
    public Assignment getAssignment(@PathVariable("title") String title) {
        return assignmentService.getAssignment(title);
    }

    @DeleteMapping("/{title}")
    public void deleteAssignment(@PathVariable("title") String title) {
        assignmentService.deleteAssignment(title);
    }

}
