package com.zidio.jobportal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zidio.jobportal.DTO.ApplicationStatusDTO;
import com.zidio.jobportal.DTO.CourseStatusDTO;
import com.zidio.jobportal.DTO.JobStatusDTO;
import com.zidio.jobportal.DTO.SubscriptionStatusDTO;
import com.zidio.jobportal.DTO.UserStatusDTO;
import com.zidio.jobportal.Enum.ApplicationStatus;
import com.zidio.jobportal.Enum.Role;
import com.zidio.jobportal.entity.Course;
import com.zidio.jobportal.entity.SubscriptionPlan;
import com.zidio.jobportal.repository.ApplicationRepository;
import com.zidio.jobportal.repository.CourseRepository;
import com.zidio.jobportal.repository.JobPostsRepository;
import com.zidio.jobportal.repository.SubscriptionPlanRepository;
import com.zidio.jobportal.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final UserRepository userRepo;
    private final JobPostsRepository jobRepo;
    private final ApplicationRepository applicationRepo;

    private final SubscriptionPlanRepository subscriptionRepo;
    private final CourseRepository courseRepo;

    // ========================= USER STATUS =========================
    public UserStatusDTO getUserStatus() {
        long totalJobseekers = userRepo.countByRole(Role.USER);
        long totalRecruiters = userRepo.countByRole(Role.RECRUITER);
        long verifiedUsers = userRepo.countByVerified(true);
        long unverifiedUsers = userRepo.countByVerified(false);

        return UserStatusDTO.builder()
                .totalJobseeker(totalJobseekers)
                .totalRecruiter(totalRecruiters)
                .verifiedUsers(verifiedUsers)
                .unverifiedUsers(unverifiedUsers)
                .build();
    }

    // ========================= JOB STATUS =========================
    public JobStatusDTO getJobStatus() {
        long totalJobs = jobRepo.count();
        long activeJobs = jobRepo.findByActiveTrue().size();
        long closedJobs = totalJobs - activeJobs;

        return JobStatusDTO.builder()
                .totalJobs(totalJobs)
                .activeJobs(activeJobs)
                .closedJobs(closedJobs)
                .build();
    }

    // ========================= APPLICATION STATUS =========================
    public ApplicationStatusDTO getApplicationStatus() {

        long applied = applicationRepo.countByStatus(ApplicationStatus.APPLIED);
        long rejected = applicationRepo.countByStatus(ApplicationStatus.REJECTED);
        long shortlisted = applicationRepo.countByStatus(ApplicationStatus.SHORTLISTED);
        long underReview = applicationRepo.countByStatus(ApplicationStatus.UNDER_REVIEW);
        long hired = applicationRepo.countByStatus(ApplicationStatus.HIRED);

        return ApplicationStatusDTO.builder()
                .totalJobApplication(applicationRepo.count())
                .appliedCount(applied)
                .rejectedCount(rejected)
                .shortlistedCount(shortlisted)
                .underReviewCount(underReview)
                .hiredCount(hired)
                .build();
    }

    // ========================= SUBSCRIPTION STATUS =========================
    public SubscriptionStatusDTO getSubscriptionStatus() {

        List<SubscriptionPlan> plans = subscriptionRepo.findAll();

        long activePlans = plans.stream().filter(SubscriptionPlan::getIsActive).count();
        long inactivePlans = plans.size() - activePlans;

        // For DTO → return only last created plan or first plan (since your structure is single)
        SubscriptionPlan latest = plans.isEmpty() ? null :
                plans.get(plans.size() - 1);

        if (latest == null) {
            return SubscriptionStatusDTO.builder()
                    .isActive(false)
                    .isInactive(true)
                    .build();
        }

        return SubscriptionStatusDTO.builder()
                .id(latest.getId())
                .planName(latest.getPlanName())
                .price(latest.getPrice())
                .currency(latest.getCurrency())
                .duration(latest.getDuration())
                .validityDays(latest.getValidityDays())
                .isActive(latest.getIsActive())
                .isInactive(!latest.getIsActive())
                .features(latest.getFeatures())
                .description(latest.getDescription())
                .createdAt(latest.getCreatedAt())
                .build();
    }

    // ========================= COURSE STATUS =========================
    public CourseStatusDTO getCourseStatus() {

        List<Course> courses = courseRepo.findAll();

        if (courses.isEmpty()) {
            return CourseStatusDTO.builder()
                    .active(false)
                    .inActive(true)
                    .build();
        }

        Course latest = courses.get(courses.size() - 1);

        return CourseStatusDTO.builder()
                .id(latest.getId())
                .courseName(latest.getCourseName())
                .category(latest.getCategory())
                .price(latest.getPrice())
                .durationHours(latest.getDurationHours())
                .courseDescription(latest.getCourseDescription())
                .certificateAvailable(latest.isCertificateAvailable())
                .active(latest.isActive())
                .inActive(!latest.isActive())
                .courseUrl(latest.getCourseUrl())
                .adminId(latest.getAdminId())
                .createdAt(latest.getCreatedAt())
                .build();
    }

    // ========================= COMBINED ANALYTICS =========================
    public Map<String, Object> getFullAnalytics() {

        Map<String, Object> analytics = new HashMap<>();

        analytics.put("users", getUserStatus());
        analytics.put("jobs", getJobStatus());
        analytics.put("applications", getApplicationStatus());
        analytics.put("subscriptions", getSubscriptionStatus());
        analytics.put("courses", getCourseStatus());

        return analytics;
    }
}
