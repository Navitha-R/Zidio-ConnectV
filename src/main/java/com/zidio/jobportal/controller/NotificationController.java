package com.zidio.jobportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zidio.jobportal.DTO.EmailRequestDTO;
import com.zidio.jobportal.DTO.InvoiceRequestDTO;
import com.zidio.jobportal.service.EmailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailService emailService;

    /**
     * Send a normal email without invoice
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequestDTO emailRequest) {
        emailService.sendEmailWithInvoice(null, emailRequest, null);
        return ResponseEntity.ok("Email sent successfully to " + emailRequest.getTo());
    }

    /**
     * Send email with invoice PDF attached
     */
    @PostMapping("/send-invoice/{paymentId}")
    public ResponseEntity<String> sendEmailWithInvoice(
            @PathVariable Long paymentId,
            @Valid @RequestBody EmailRequestDTO emailRequest,
            @RequestBody InvoiceRequestDTO invoiceRequest) {

        emailService.sendEmailWithInvoice(paymentId, emailRequest, invoiceRequest);
        return ResponseEntity.ok("Email with invoice sent successfully to " + emailRequest.getTo());
    }
}
