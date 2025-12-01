package com.zidio.jobportal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.pulsar.PulsarProperties.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zidio.jobportal.DTO.AdminActionLogDTO;
import com.zidio.jobportal.entity.*;
import com.zidio.jobportal.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminActionLogService {

    private final AdminActionLogRepository logRepository;
 //   private final AdminActionLogRepository adminRepository;
    private final UserRepository userRepository;
    private final RecruiterRepository recruiterRepository;
    private final JobPostsRepository jobPostsRepository;
    private final ApplicationRepository applicationRepository;

    // ✅ Create new log
    public AdminActionLogDTO createLog(AdminActionLogDTO dto) {

        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        User targetUser = userRepository.findById(dto.getTargetUserId())
                .orElseThrow(() -> new RuntimeException("Target user not found"));

        Recruiter recruiter = dto.getRecruiterId() != null
                ? recruiterRepository.findById(dto.getRecruiterId()).orElse(null)
                : null;

        JobPosts jobPost = dto.getJobPostId() != null
                ? jobPostsRepository.findById(dto.getJobPostId()).orElse(null)
                : null;

        Application application = dto.getApplicationId() != null
                ? applicationRepository.findById(dto.getApplicationId()).orElse(null)
                : null;

        AdminActionLog log = AdminActionLog.builder()
                .admin(admin)
                .targetUser(targetUser)
                .recruiter(recruiter)
                .jobPost(jobPost)
                .application(application)
                .action(dto.getAction())
                .description(dto.getDescription())
                .build();

        logRepository.save(log);

        dto.setId(log.getId());
        dto.setTimestamp(log.getTimestamp());
        return dto;
    }

    // ✅ Fetch all logs
    public List<AdminActionLogDTO> getAllLogs() {
        return logRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Fetch logs by admin
    public List<AdminActionLogDTO> getLogsByAdmin(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        return logRepository.findByAdmin(admin)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method
    private AdminActionLogDTO convertToDTO(AdminActionLog log) {
        return AdminActionLogDTO.builder()
                .id(log.getId())
                .adminId(log.getAdmin().getId())
                .targetUserId(log.getTargetUser().getId())
                .recruiterId(log.getRecruiter() != null ? log.getRecruiter().getId() : null)
                .jobPostId(log.getJobPost() != null ? log.getJobPost().getId() : null)
                .applicationId(log.getApplication() != null ? log.getApplication().getId() : null)
                .action(log.getAction())
                .description(log.getDescription())
                .timestamp(log.getTimestamp())
                .build();
    }
}
