package com.zidio.jobportal.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.jobportal.DTO.InvoiceRequestDTO;
import com.zidio.jobportal.entity.Payment;
import com.zidio.jobportal.service.GeneratedInvoiceService;
import com.zidio.jobportal.service.PaymentService;

@RestController
@RequestMapping("/api/invoice")
public class GeneratedInvoiceController {

    private final GeneratedInvoiceService invoiceService;
    private final PaymentService paymentService;

    public GeneratedInvoiceController(GeneratedInvoiceService invoiceService, PaymentService paymentService) {
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
    }

    // Generate invoice by Payment ID
    @PostMapping("/generate/{paymentId}")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable Long paymentId,
                                                  @RequestBody InvoiceRequestDTO request) {

        // Fetch payment from DB
        Payment payment = paymentService.getPaymentById(paymentId);

        if (payment == null) {
            return ResponseEntity.notFound().build();
        }

        // Generate PDF using the service
        byte[] pdfBytes = invoiceService.generateInvoicePdf(request, payment);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + paymentId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
