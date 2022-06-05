package com.emeritus.course.repository;

import com.emeritus.course.model.Assignment;
import org.springframework.data.repository.CrudRepository;

public interface AssignmentRepository extends CrudRepository<Assignment, String> {
}
