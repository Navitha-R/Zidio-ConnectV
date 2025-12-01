package com.zidio.jobportal.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.zidio.jobportal.entity.Recruiter;
import com.zidio.jobportal.entity.User;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    boolean existsByCompanyEmail(String companyEmail);

   
    Optional<Recruiter>findByUser(User user);
}
