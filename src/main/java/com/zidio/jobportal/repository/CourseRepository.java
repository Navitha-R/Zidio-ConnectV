package com.zidio.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zidio.jobportal.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
	 
       //Check if course already exists
       boolean existsByCourseName(String courseName);

        // Fetch courses by level (BEGINNER / INTERMEDIATE / ADVANCED)
      //  List<Course> findByLevel(String level);

         // Fetch courses by category (Programming / Cloud / AI etc.)
          List<Course> findByCategory(String category);

         // Fetch only active courses (real-world usage)
          List<Course> findByActiveTrue();

         // Find course by name (useful in service layer)
         Course findByCourseName(String courseName);
}