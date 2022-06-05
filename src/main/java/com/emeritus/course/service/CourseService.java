package com.emeritus.course.service;

import com.emeritus.course.exception.DataNotFoundException;
import com.emeritus.course.model.Course;
import com.emeritus.course.model.EmeritusUser;
import com.emeritus.course.repository.CourseRepository;
import com.emeritus.course.repository.UserRepository;
import com.emeritus.course.util.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    public Iterable<Course> getAllCourses(Principal principal) {
        return getCoursesByUser(principal);
    }

    private Iterable<Course> getCoursesByUser(Principal principal) {
        EmeritusUser user = userService.getUser(principal.getName());
        if(Roles.INSTRUCTOR.getRole().equalsIgnoreCase(user.getRole())){
            return user.getCourseEnrollment();
        }
        return courseRepository.findAll();
    }

    private boolean isLoggedInUserInstructor(Principal principal){
        EmeritusUser user = userService.getUser(principal.getName());
        if(Roles.INSTRUCTOR.getRole().equalsIgnoreCase(user.getRole())){
            return true;
        }
        return false;
    }

    public Course saveCourse(Course course) {
        course.setCreatedTs(new Date());
        return courseRepository.save(course);
    }

    public Course getCourse(String title, Principal principal) {
        boolean isUserAnInstructor = isLoggedInUserInstructor(principal);
        if(isUserAnInstructor){
            return courseRepository.findByCourseTitleAndUser(title, principal.getName());
        }
        Optional<Course> optionalCourse = courseRepository.findById(title);
        if(optionalCourse.isPresent()){
            return optionalCourse.get();
        }
        throw new DataNotFoundException(Course.class.getSimpleName(), title);
    }

    public void deleteCourse(String title, Principal principal) {
        Course course = getCourse(title, principal);
        courseRepository.deleteById(course.getTitle());
    }
}
