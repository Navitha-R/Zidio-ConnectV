package com.zidio.jobportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zidio.jobportal.Enum.Role;
import com.zidio.jobportal.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUsername(String username);

    Optional<User> findBySubscriptionId(Long subscriptionId);

    // ✅ Correct method (must match field name in User entity)
    Optional<User> findByResetToken(String resetToken);

    boolean existsByUserEmail(String userEmail);

    boolean existsByUsername(String username);

    List<User> findByActiveTrue();

    List<User> findByVerifiedFalse();

    long countByRole(Role role);

    long countByVerified(boolean verified);

    long countByActive(boolean active);
}
