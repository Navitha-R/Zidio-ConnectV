package com.zidio.jobportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.jobportal.DTO.CourseDTO;
import com.zidio.jobportal.entity.Course;
import com.zidio.jobportal.service.CourseService;

import lombok.RequiredArgsConstructor;

    @RestController
	@RequestMapping("/api/courses")
	@RequiredArgsConstructor
	public class CourseController {
	private final CourseService courseService;

    // 1️⃣ Add new course (Admin only in real-world scenario)
    @PostMapping("/add")
    public ResponseEntity<Course> addCourse(@RequestBody CourseDTO courseDTO) {
        Course savedCourse = courseService.addCourse(courseDTO);
        return ResponseEntity.ok(savedCourse);
    }

    // 2️⃣ Get all courses (Active courses only)
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // 3️⃣ Get course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    // 4️⃣ Update course by ID (Admin only)
    @PutMapping("/update/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        Course updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    // 5️⃣ Soft delete course by ID (Admin only)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course with ID " + id + " deactivated successfully");
    }

    // 6️⃣ Get courses by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Course>> getCoursesByCategory(@PathVariable String category) {
        List<Course> courses = courseService.getCoursesByCategory(category);
        return ResponseEntity.ok(courses);
    }

	   
	}


