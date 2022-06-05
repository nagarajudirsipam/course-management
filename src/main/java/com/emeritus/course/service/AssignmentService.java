package com.emeritus.course.service;

import com.emeritus.course.model.Assignment;
import com.emeritus.course.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentService {
    
    @Autowired
    AssignmentRepository assignmentRepository;

    public Iterable<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment saveAssignment(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }

    public Assignment getAssignment(String title) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(title);
        return optionalAssignment.orElse(null);
    }

    public void deleteAssignment(String title) {
        assignmentRepository.deleteById(title);
    }

}
