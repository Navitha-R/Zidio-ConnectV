package com.zidio.jobportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zidio.jobportal.Enum.JobType;
import com.zidio.jobportal.entity.JobPosts;

@Repository
public interface JobPostsRepository extends JpaRepository<JobPosts,Long> {
	
	List<JobPosts>findByRecruiterId(Long recruiterId);//find all jobposts created by spcific recruiter...
	List<JobPosts>findByJobType(JobType jobType);//find jobs by type(eg:fulltime,parttime,etc)...
	List<JobPosts>findByJobTitleContainingIgnoreCase(String keyword);//case insentive
	List<JobPosts>findByJobLocationContainingIgnoreCase(String Location);
	List<JobPosts>findByCompanyNameContainingIgnoreCase(String companyName);
	Optional<JobPosts>findByJobTitleAndCompanyName(String jobTitle, String companyName);
	List<JobPosts> findByActiveTrue();
	

}
