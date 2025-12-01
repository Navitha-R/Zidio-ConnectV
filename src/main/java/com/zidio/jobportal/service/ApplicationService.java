package com.zidio.jobportal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zidio.jobportal.DTO.ApplicationDTO;
import com.zidio.jobportal.Enum.ApplicationStatus;
import com.zidio.jobportal.entity.Application;
import com.zidio.jobportal.entity.JobPosts;
import com.zidio.jobportal.entity.Jobseeker;
import com.zidio.jobportal.entity.Recruiter;
import com.zidio.jobportal.repository.ApplicationRepository;
import com.zidio.jobportal.repository.JobPostsRepository;
import com.zidio.jobportal.repository.JobseekerRepository;
import com.zidio.jobportal.repository.RecruiterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {
	
	private final ApplicationRepository applicationRepository;
	private final JobseekerRepository jobseekerRepository;
	private final JobPostsRepository jobPostsRepository;
	private final RecruiterRepository recruiterRepository;
	
	public Application applyForJob(ApplicationDTO dto) {
		Jobseeker jobseeker = jobseekerRepository.findById(dto.getJobSeekerId())
				.orElseThrow(() -> new RuntimeException("Jobseeker not found"));
		
		JobPosts job= jobPostsRepository.findById(dto.getJobId())
				.orElseThrow(() -> new RuntimeException("job not found"));
		
         Recruiter recruiter = recruiterRepository.findById(dto.getRecruiterId())
        		 .orElseThrow(() -> new RuntimeException("Recruiter not found"));
      // prevent duplicate applications
         boolean alreadyApplied = applicationRepository.findByJobSeekerId(jobseeker.getId())
        		 .stream()
        		 .anyMatch(app -> app.getJob().getId().equals(job.getId()));
         if(alreadyApplied) {
        	 throw new RuntimeException("you already applied for this job");
        	 
         }
         Application application =Application.builder()
        		 .job(job)
        		 .jobSeeker(jobseeker)
        		 .recruiter(recruiter)
        		 .status(ApplicationStatus.APPLIED)  
        		 .build();
         
         return applicationRepository.save(application);
		
	}
	public List<Application> getApplicationByJobseeker(Long jobseekerId){
		return applicationRepository.findByJobSeekerId(jobseekerId);
	    }
	
	public List<Application> getApplicationByRecruiter(Long recruiterId){
		return applicationRepository.findByRecruiterId(recruiterId);
	    }
	public void updateStatus(Long applicationId, ApplicationStatus status) {
		Application application = applicationRepository.findById(applicationId)
				.orElseThrow(() -> new RuntimeException("Application not found"));
				application.setStatus(status);
		        applicationRepository.save(application);
	
	          }


}
