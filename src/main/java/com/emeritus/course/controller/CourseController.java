package com.emeritus.course.controller;

import com.emeritus.course.model.Course;
import com.emeritus.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

@RestController
@RequestMapping(path = "/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public Iterable<Course> getAllCourses(Principal principal) {
        return courseService.getAllCourses(principal);
    }

    @PostMapping(headers = HttpHeaders.AUTHORIZATION)
    @Secured({ "INSTRUCTOR" })
    public Course saveCourse(@Valid @RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @GetMapping(path = "/{title}", headers = HttpHeaders.AUTHORIZATION)
    @Secured({ "INSTRUCTOR", "SYSTEM_ADMIN", "STUDENT" })
    public Course getCourse(@Validated @PathVariable("title") String title, Principal principal) {
        return courseService.getCourse(title, principal);
    }

    @DeleteMapping(path = "/{title}", headers = HttpHeaders.AUTHORIZATION)
    @Secured({ "INSTRUCTOR" })
    public void deleteCourse(@Validated @PathVariable("title") String title, Principal principal) {
        courseService.deleteCourse(title, principal);
    }
}
