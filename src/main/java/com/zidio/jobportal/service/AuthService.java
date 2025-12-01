package com.zidio.jobportal.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zidio.jobportal.DTO.ForgotPasswordRequestDTO;
import com.zidio.jobportal.DTO.LoginRequestDTO;
import com.zidio.jobportal.DTO.ResetPasswordRequestDTO;
import com.zidio.jobportal.entity.User;
import com.zidio.jobportal.repository.BlockListedTokenRepository;
import com.zidio.jobportal.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BlockListedTokenRepository blockListedTokenRepository;

    @Autowired
    private EmailService emailService; // You must create this class

    // ---------- LOGIN ----------------
    public String login(LoginRequestDTO loginRequestDTO) {

        String email = loginRequestDTO.getEmail();
        String password = loginRequestDTO.getPassword();

        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return "Login successful for: " + user.getUserName();
    }

    // ---------- FORGOT PASSWORD ----------------
    public String forgotPassword(ForgotPasswordRequestDTO request) {

        User user = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + request.getUserEmail()));

        // Generate token & expiry (valid 15 minutes)
        String resetToken = UUID.randomUUID().toString();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(15);

        user.setResetToken(resetToken);
        user.setResetTokenExpire(expiry);
        userRepository.save(user);

        // Reset link
        String resetLink = "https://your-frontend-domain.com/reset-password?token=" + resetToken;

        // Send email
        String subject = "Password Reset Request";
        String body = "Hi " + user.getUserName() + ",\n\n"
                + "Click the link below to reset your password:\n"
                + resetLink + "\n\n"
                + "Note: This link is valid for only 15 minutes.\n\n"
                + "Regards,\nJobPortal Team";

        emailService.sendEmail(user.getUserEmail(), subject, body);

        return "Password reset link has been sent to your email.";
    }

    // ---------- RESET PASSWORD ----------------
    public String resetPassword(ResetPasswordRequestDTO request) {

        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        // Check token expiry
        if (user.getResetTokenExpire().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token expired. Please request again.");
        }

        // Set new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Remove token after use
        user.setResetToken(null);
        user.setResetTokenExpire(null);

        userRepository.save(user);

        return "Password reset successful! You can now login with your new password.";
    }
}
