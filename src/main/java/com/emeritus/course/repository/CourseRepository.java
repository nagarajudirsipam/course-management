package com.emeritus.course.repository;

import com.emeritus.course.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends CrudRepository<Course, String> {
    @Query("SELECT DISTINCT c FROM Course c JOIN c.emeritusUsers e WHERE c.title = :title AND e.userName = :userName")
    Course findByCourseTitleAndUser(@Param("title") String title, @Param("userName") String userName);
}
