package com.zidio.jobportal.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.zidio.jobportal.entity.Jobseeker;

public interface JobseekerRepository extends JpaRepository<Jobseeker, Long> {

    // ✅ Check if an email already exists (useful for validation)
    boolean existsByJobseekerEmail(String email);

    // ✅ Find jobseeker by linked user ID (useful for profile lookup)
    Optional<Jobseeker> findByUserId(Long userId);
}
