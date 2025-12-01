package com.zidio.jobportal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.itextpdf.text.List;
import com.zidio.jobportal.DTO.PaymentRequestDTO;
import com.zidio.jobportal.DTO.PaymentResponseDTO;
import com.zidio.jobportal.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // -------------------------------
    // 1️⃣ Create a payment entry
    // -------------------------------
    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentRequestDTO dto) {
        PaymentResponseDTO response = paymentService.createPayment(dto);
        return ResponseEntity.ok(response);
    }

    // -------------------------------
    // 2️⃣ Get all payments of a user
    // -------------------------------
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByUser(@PathVariable String userId) {

        List<PaymentResponseDTO> list = paymentService.getPaymentsByUserId(String userId);

        return ResponseEntity.ok(list);
    }

    // -------------------------------
    // 3️⃣ Get payment by transactionId
    // -------------------------------
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByTransactionId(@PathVariable String transactionId) {

        PaymentResponseDTO response = paymentService.getPaymentByTransactionId(String transactionId);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}