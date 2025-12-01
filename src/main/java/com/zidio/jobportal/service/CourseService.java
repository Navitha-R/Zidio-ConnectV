package com.zidio.jobportal.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.zidio.jobportal.DTO.CourseDTO;
import com.zidio.jobportal.entity.Course;
import com.zidio.jobportal.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

	@Service
	@RequiredArgsConstructor
	public class CourseService {
		private final CourseRepository courseRepository;

	    // Add a new course
	    public Course addCourse(CourseDTO courseDTO) {
	        // Check if course with same name exists
	        if (courseRepository.existsByCourseName(courseDTO.getCourseName())) {
	            throw new RuntimeException("Course with name '" + courseDTO.getCourseName() + "' already exists");
	        }

	        Course course = Course.builder()
	                .courseName(courseDTO.getCourseName())
	                //.level(courseDTO.getLevel())
	                .category(courseDTO.getCategory())
	                .courseUrl(courseDTO.getCourseUrl())
	                .certificateAvailable(courseDTO.isCertificateAvailable())
	                .adminId(courseDTO.getAdminId())
	                .courseDescription(courseDTO.getCourseDescription())
	                .active(true) // default to true
	                .build();

	        return courseRepository.save(course);
	    }

	    // Get all courses
	    public List<Course> getAllCourses() {
	        return courseRepository.findAll();
	    }

	    // Get course by ID
	    public Course getCourseById(Long id) {
	        Optional<Course> courseOpt = courseRepository.findById(id);
	        if (courseOpt.isEmpty()) {
	            throw new RuntimeException("Course with ID " + id + " not found");
	        }
	        return courseOpt.get();
	    }

	    // Update course by ID
	    public Course updateCourse(Long id, CourseDTO courseDTO) {
	        Course course = getCourseById(id); // reuse getCourseById for validation

	        course.setCourseName(courseDTO.getCourseName());
	        //course.setLevel(courseDTO.getLevel());
	        course.setCategory(courseDTO.getCategory());
	        course.setCourseUrl(courseDTO.getCourseUrl());
	        course.setCertificateAvailable(courseDTO.isCertificateAvailable());
	        course.setAdminId(courseDTO.getAdminId());
	        course.setCourseDescription(courseDTO.getCourseDescription());
	        // optionally update active if needed
	         course.setActive(courseDTO.isActive());

	        return courseRepository.save(course);
	    }

	    // Delete course by ID (soft delete can be used by setting active=false)
	    public void deleteCourse(Long id) {
	        Course course = getCourseById(id);
	        course.setActive(false); // soft delete
	        courseRepository.save(course);
	    }

	    // Get courses by level
	   // public List<Course> getCoursesByLevel(String level) {
	      //  return courseRepository.findByLevel(level);
	    //}

	    // Get courses by category
	    public List<Course> getCoursesByCategory(String category) {
	        return courseRepository.findByCategory(category);
	    }

	    
}
