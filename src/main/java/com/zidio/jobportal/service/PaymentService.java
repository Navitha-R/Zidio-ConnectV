package com.zidio.jobportal.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.zidio.jobportal.DTO.EmailRequestDTO;
import com.zidio.jobportal.DTO.InvoiceRequestDTO;
import com.zidio.jobportal.DTO.PaymentRequestDTO;
import com.zidio.jobportal.DTO.PaymentResponseDTO;
import com.zidio.jobportal.Enum.PaymentStatus;
import com.zidio.jobportal.entity.Payment;
import com.zidio.jobportal.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final GeneratedInvoiceService invoiceService;
    private final EmailService emailService;   // 🔥 attach invoice email

    public PaymentResponseDTO createPayment(PaymentRequestDTO dto) {

        // 1️⃣ Create new payment object
        Payment payment = Payment.builder()
                .userId(dto.getUserId())
                .planId(dto.getPlanId())
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .paymentType(dto.getPaymentType())
                .paymentStatus(PaymentStatus.SUCCESS)
                .transactionId("TXN" + System.currentTimeMillis())
                .timeStamp(LocalDateTime.now())
                .build();

        // 2️⃣ Save into DB
        Payment saved = paymentRepository.save(payment);

        // 3️⃣ Generate invoice PDF using your GeneratedInvoiceService
        byte[] pdfBytes = invoiceService.generateInvoicePdf(dto.getInvoiceRequest(), saved);

        // 4️⃣ Save PDF to folder
        String folderPath = "invoices/";
        File directory = new File(folderPath);
        if (!directory.exists()) directory.mkdirs();

        String filePath = folderPath + "Invoice-" + saved.getTransactionId() + ".pdf";

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(pdfBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save invoice PDF", e);
        }

        // 5️⃣ Send Invoice Email
        EmailRequestDTO email = new EmailRequestDTO();
        email.setTo(dto.getInvoiceRequest().getBillingInfo().getEmail());
        email.setSubject("Your Invoice - " + saved.getTransactionId());
        email.setBody("Dear Customer,\n\nPlease find your invoice attached.\n\nThank you!");

        InvoiceRequestDTO invoiceReq = dto.getInvoiceRequest();

        // 🔥 send invoice using your EmailService
        emailService.sendEmailWithInvoice(saved.getId(), email, invoiceReq);

        // 6️⃣ Build Response DTO
        return PaymentResponseDTO.builder()
                .paymentId(saved.getId())
                .userId(saved.getUserId())
                .planId(saved.getPlanId())
                .amount(saved.getAmount())
                .currency(saved.getCurrency())
                .paymentStatus(saved.getPaymentStatus())
                .paymentType(saved.getPaymentType())
                .transactionId(saved.getTransactionId())
                .timeStamp(saved.getTimeStamp().toString())
                .build();
    }
}
