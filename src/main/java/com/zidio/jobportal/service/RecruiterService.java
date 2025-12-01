package com.zidio.jobportal.service;

import org.springframework.stereotype.Service;
import com.zidio.jobportal.DTO.RecruiterDTO;
import com.zidio.jobportal.entity.Recruiter;
import com.zidio.jobportal.entity.User;
import com.zidio.jobportal.repository.RecruiterRepository;

@Service
public class RecruiterService {
	
	private final RecruiterRepository recruiterRepository;
	//constructor injection
    public RecruiterService(RecruiterRepository recruiterRepository) {
    	this.recruiterRepository=recruiterRepository;
    }
	//create recruiter profile...
	public Recruiter createRecruiterProfile(User user, RecruiterDTO dto) {
		if(recruiterRepository.existsByCompanyEmail(dto.getCompanyEmail())) {
			throw new RuntimeException("company email already exists");
		}
		/*
		/Buildermethod:
		/Use builder in createRecruiterProfile() → new object
/build recruiter entity from dto...Builder method becoz Cleaner, readable, avoids missing fields, chainable...
		/Builder = You’re building a brand new house: choose walls, doors, windows, furniture.*/
		Recruiter recruiter=Recruiter.builder()
				.user(user)
				.companyName(dto.getCompanyName())
				.companyEmail(dto.getCompanyEmail())
				.companyDescription(dto.getCompanyDescription())
				.websiteUrl(dto.getWebsiteUrl())
				.contactNumber(dto.getContactNumber())
				.build();
		//saving recruiter profile in database....
		return recruiterRepository.save(recruiter);
				
	}
	//fetch recruiter by linked user...
	 public Recruiter getRecruiterByUser(User user) {
	        return recruiterRepository.findByUser(user)
	                .orElseThrow(() -> new RuntimeException("Recruiter profile not found for user: "
	        + user.getUserEmail()));
	    }
	 //update recruiter profile....setter method:-Only update what you need, avoid overwriting other fields
	 //Setters = You already have a house, just repaint walls or change windows...
	 //Use setters in updateRecruiterProfile() → update only required fields..
	 public Recruiter updateRecruiterProfile(User user, RecruiterDTO dto) {
		 Recruiter recruiter=getRecruiterByUser(user);
		 recruiter.setCompanyName(dto.getCompanyName());
		 recruiter.setCompanyEmail(dto.getCompanyEmail());
		 recruiter.setCompanyDescription(dto.getCompanyDescription());
		 recruiter.setWebsiteUrl(dto.getWebsiteUrl());
		 recruiter.setContactNumber(dto.getContactNumber());
		 return recruiterRepository.save(recruiter);
		 
	}
	
	

}
