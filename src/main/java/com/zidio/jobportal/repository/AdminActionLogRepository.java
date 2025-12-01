package com.zidio.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zidio.jobportal.entity.AdminActionLog;

@Repository
public interface AdminActionLogRepository extends JpaRepository<AdminActionLog, Long> {  
	
	List<AdminActionLog> findByAdminId(Long adminId);
	List<AdminActionLog> findByTargetUserId(Long targetUserId);
	
	

}
