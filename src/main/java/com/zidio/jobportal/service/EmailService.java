package com.zidio.jobportal.service;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.zidio.jobportal.DTO.EmailRequestDTO;
import com.zidio.jobportal.DTO.InvoiceRequestDTO;
import com.zidio.jobportal.entity.Payment;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final GeneratedInvoiceService invoiceService;
    private final PaymentService paymentService;

    /**
     * Send an email with optional invoice PDF attached.
     * If invoiceRequest is provided, it generates a PDF invoice for the paymentId.
     */
    public void sendEmailWithInvoice(Long paymentId, EmailRequestDTO emailRequest, InvoiceRequestDTO invoiceRequest) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set email basic info
            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getBody(), false);

            // CC
            if (emailRequest.getCc() != null && !emailRequest.getCc().isEmpty()) {
                helper.setCc(emailRequest.getCc().toArray(new String[0]));
            }

            // BCC
            if (emailRequest.getBcc() != null && !emailRequest.getBcc().isEmpty()) {
                helper.setBcc(emailRequest.getBcc().toArray(new String[0]));
            }

            // Attach additional files if provided
            List<String> attachments = emailRequest.getAttachments();
            if (attachments != null && !attachments.isEmpty()) {
                for (String filePath : attachments) {
                    helper.addAttachment(filePath.substring(filePath.lastIndexOf("/") + 1),
                            new java.io.File(filePath));
                }
            }

            // -----------------------------
            // Generate invoice PDF attachment if invoiceRequest is provided
            // -----------------------------
            if (invoiceRequest != null && paymentId != null) {
                Payment payment = paymentService.getPaymentById(paymentId);
                if (payment == null) {
                    throw new RuntimeException("Payment not found with ID: " + paymentId);
                }

                // Generate PDF bytes
                byte[] pdfBytes = invoiceService.generateInvoicePdf(invoiceRequest, payment);

                // Attach PDF as byte stream
                ByteArrayResource resource = new ByteArrayResource(pdfBytes);
                String fileName = "Invoice-" + payment.getTransactionId() + ".pdf";
                helper.addAttachment(fileName, resource);
            }

            // Send the email
            mailSender.send(message);
            System.out.println("📩 Email sent successfully to: " + emailRequest.getTo());

        } catch (MessagingException e) {
            throw new RuntimeException("❌ Failed to send email: " + e.getMessage(), e);
        }
    }
}
