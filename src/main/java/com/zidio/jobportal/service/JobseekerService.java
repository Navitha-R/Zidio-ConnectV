package com.zidio.jobportal.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zidio.jobportal.DTO.JobseekerDTO;
import com.zidio.jobportal.entity.Jobseeker;
import com.zidio.jobportal.repository.JobseekerRepository;

@Service
public class JobseekerService {

    @Autowired
    private JobseekerRepository jobseekerRepository;

    /**
     * ✅ Create a new jobseeker profile (from DTO)
     */
    public Jobseeker createProfile(JobseekerDTO dto) {
        // 1️⃣ Prevent duplicate email registration
        if (jobseekerRepository.existsByJobseekerEmail(dto.getJobseekerEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // 2️⃣ Convert DTO → Entity
        Jobseeker jobseeker = Jobseeker.builder()
                .userId(dto.getUserId())
                .jobseekerFullname(dto.getJobseekerFullname())
                .jobseekerEmail(dto.getJobseekerEmail())
                .universityName(dto.getUniversityName())
                .educationalStream(dto.getEducationalStream())
                .passedOutYear(dto.getPassedOutYear())
                .skills(dto.getSkills())
                .resumeUrl(dto.getResumeUrl())
                .certificateJpg(dto.getCertificateJpg())
                .build();

        // 3️⃣ Save to database
        return jobseekerRepository.save(jobseeker);
    }

    /**
     * ✅ Get jobseeker profile by userId
     */
    public Jobseeker getJobseekerProfile(Long userId) {
        return jobseekerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Jobseeker not found"));
    }
}

