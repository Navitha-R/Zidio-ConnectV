package com.zidio.jobportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zidio.jobportal.DTO.RecruiterDTO;
import com.zidio.jobportal.entity.Recruiter;
import com.zidio.jobportal.entity.User;
import com.zidio.jobportal.service.RecruiterService;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {
	private final RecruiterService recruiterService;
	
    //construction injection for service...
	public RecruiterController(RecruiterService recruiterService) {
		this.recruiterService=recruiterService;
	  }
	//create a new recruiter profile...post/api/recruiters/create
	@PostMapping("/create")
	 public ResponseEntity<Recruiter> createRecruiter(@RequestBody RecruiterDTO dto,
			                                          @RequestParam Long userId) {
	        // In real-world, you would fetch user from DB or authentication token
	        User user = new User();
	        user.setId(userId);
	        Recruiter recruiter= recruiterService.createRecruiterProfile(user, dto);
	        		return ResponseEntity.ok(recruiter);
	}
	 @GetMapping("/user/{userId}")
		 public ResponseEntity<Recruiter> getRecruiterByUser(@PathVariable Long userId )	{
			 User user = new User();
		        user.setId(userId);

		        Recruiter recruiter = recruiterService.getRecruiterByUser(user);
		        return ResponseEntity.ok(recruiter);
			  }
//update profile  Update recruiter profile
   //  * PUT /api/recruiters/update?userId={userId}
	 @PutMapping("/update")
 public ResponseEntity<Recruiter> updateRecruiter(@RequestParam Long userId,
		                                          @RequestBody RecruiterDTO dto) {
	        // In real app, fetch user from DB or 
	        User user = new User();
	        user.setId(userId);

	        Recruiter updatedRecruiter = recruiterService.updateRecruiterProfile(user, dto);
	        return ResponseEntity.ok(updatedRecruiter);
	    }
			 }
		 

