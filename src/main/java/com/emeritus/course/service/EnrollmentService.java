package com.emeritus.course.service;

import com.emeritus.course.exception.DataNotFoundException;
import com.emeritus.course.model.Assignment;
import com.emeritus.course.model.Course;
import com.emeritus.course.model.EmeritusUser;
import com.emeritus.course.repository.AssignmentRepository;
import com.emeritus.course.repository.CourseRepository;
import com.emeritus.course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    public EmeritusUser enrollInCourse(String userName, String courseTitle) {
        Optional<EmeritusUser> emeritusUserOptional = userRepository.findById(userName);
        if(!emeritusUserOptional.isPresent()){
            throw new DataNotFoundException(EmeritusUser.class.getSimpleName(), userName);
        }
        EmeritusUser user = emeritusUserOptional.get();
        Optional<Course> courseOptional = courseRepository.findById(courseTitle);
        if(!courseOptional.isPresent()){
            throw new DataNotFoundException(Course.class.getSimpleName(), courseTitle);
        }
        Course course = courseOptional.get();
        user.getCourseEnrollment().add(course);
        return userRepository.save(user);
    }

    public Course enrollAssignmentInCourse(String userName, String courseTitle, String assignmentTitle) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentTitle);
        if(!assignmentOptional.isPresent()){
            throw new DataNotFoundException(Assignment.class.getSimpleName(), assignmentTitle);
        }
        Assignment assignment = assignmentOptional.get();
        Optional<Course> courseOptional = courseRepository.findById(courseTitle);
        if(!courseOptional.isPresent()){
            throw new DataNotFoundException(Course.class.getSimpleName(), courseTitle);
        }
        Course course = courseOptional.get();
        course.getAssignments().add(assignment);
        return courseRepository.save(course);
    }

    public EmeritusUser enrollInAssignment(String userName, String assignmentTitle) {
        Optional<EmeritusUser> emeritusUserOptional = userRepository.findById(userName);
        if(!emeritusUserOptional.isPresent()){
            throw new DataNotFoundException(EmeritusUser.class.getSimpleName(), userName);
        }
        EmeritusUser user = emeritusUserOptional.get();
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentTitle);
        if(!assignmentOptional.isPresent()){
            throw new DataNotFoundException(Assignment.class.getSimpleName(), assignmentTitle);
        }
        Assignment assignment = assignmentOptional.get();
        user.getAssignmentEnrollment().add(assignment);
        return userRepository.save(user);
    }
}
