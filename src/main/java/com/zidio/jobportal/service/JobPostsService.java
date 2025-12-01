package com.zidio.jobportal.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zidio.jobportal.DTO.JobPostsDTO;
import com.zidio.jobportal.Enum.JobType;
import com.zidio.jobportal.entity.JobPosts;
import com.zidio.jobportal.entity.Recruiter;
import com.zidio.jobportal.repository.JobPostsRepository;
import com.zidio.jobportal.repository.RecruiterRepository;


@Service

public class JobPostsService {

    private final JobPostsRepository jobPostsRepository;
    private final RecruiterRepository recruiterRepository;

    //Constructor Injection:-
    public JobPostsService(JobPostsRepository jobPostsRepository, RecruiterRepository recruiterRepository) {
        this.jobPostsRepository = jobPostsRepository;
        this.recruiterRepository = recruiterRepository;
    }

    // Create a new Job Post:-
    public JobPosts createJobPost(Long recruiterId, JobPostsDTO dto) {
        // Fetch recruiter entity
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found with ID: " + recruiterId));

        // Build and save new Job Post
        JobPosts jobPost = JobPosts.builder()
                .recruiter(recruiter)
                .jobTitle(dto.getJobTitle())
                .jobDescription(dto.getJobDescription())
                .companyName(dto.getCompanyName())
                .jobLocation(dto.getJobLocation())
                .jobType(dto.getJobtype())
                .remote(dto.getRemote())
                .salary(dto.getSalary())
                .experienceLevel(dto.getExperienceLevel())
                .skillsRequired(dto.getSkillsRequired())
                .postedDate(LocalDateTime.now())
                .deadlineDate(dto.getDeadlineDate() != null
                        ? dto.getDeadlineDate()
                        : LocalDateTime.now().plusDays(30))
                .active(true)
                .build();

        return jobPostsRepository.save(jobPost);
    }

    // Get all jobs created by a specific recruiter
    public List<JobPosts> getJobsByRecruiter(Long recruiterId) {
        return jobPostsRepository.findByRecruiterId(recruiterId);
    }
    // Search jobs by job title
    public List<JobPosts> searchByTitle(String title) {
        return jobPostsRepository.findByJobTitleContainingIgnoreCase(title);
    }
    //Search jobs by company name
    public List<JobPosts> searchByCompanyName(String companyName) {
        return jobPostsRepository.findByCompanyNameContainingIgnoreCase(companyName);
    }
    //Search jobs by location
    public List<JobPosts> searchByLocation(String location) {
        return jobPostsRepository.findByJobLocationContainingIgnoreCase(location);
    }
    //  Filter jobs by type (e.g., FULL_TIME, PART_TIME)
    public List<JobPosts> filterByJobType(JobType jobType) {
        return jobPostsRepository.findByJobType(jobType);
    }
    // Deactivate a job (soft delete)
    public void deactivateJob(Long jobId) {
        JobPosts job = jobPostsRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));
        job.setActive(false);
        jobPostsRepository.save(job);
    }
    // Get all active jobs
    public List<JobPosts> getActiveJobs() {
        return jobPostsRepository.findByActiveTrue();
    }
    // Get a job by ID
    public JobPosts getJobById(Long id) {
        return jobPostsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));
    }
}

