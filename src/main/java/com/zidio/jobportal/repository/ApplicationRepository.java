package com.zidio.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zidio.jobportal.Enum.ApplicationStatus;
import com.zidio.jobportal.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>{
	
	List<Application> findByJobSeekerId(Long jobSeekerId);
	List<Application> findByRecruiterId(Long recruiterId);
	List<Application> findByjobId(Long jobId);
	long countByStatus(ApplicationStatus status);

	

}
