package com.zidio.jobportal.service;

    import com.itextpdf.text.*;
	import com.itextpdf.text.pdf.*;
	import com.zidio.jobportal.DTO.BillingInfo;
	import com.zidio.jobportal.DTO.InvoiceItem;
	import com.zidio.jobportal.DTO.InvoiceRequestDTO;
	import com.zidio.jobportal.entity.Payment;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.stereotype.Service;

	import java.io.ByteArrayOutputStream;
	import java.io.FileOutputStream;
	import java.math.BigDecimal;
	import java.text.DateFormat;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import java.util.List;

	@Service
	public class GeneratedInvoiceService {

	    // you can configure these via application.properties
	    @Value("${invoice.company.name:Zidio Private Limited}")
	    private String companyName;

	    @Value("${invoice.company.address:123, Example Street, City}")
	    private String companyAddress;

	    @Value("${invoice.company.email:info@zidio.com}")
	    private String companyEmail;

	    @Value("${invoice.logo.path:}") // optional logo file path
	    private String logoPath;

	    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	    /**
	     * Generate invoice PDF as byte[] using iText 5.
	     * This method produces a complete invoice with header, items, totals and QR code.
	     */
	    public byte[] generateInvoicePdf(InvoiceRequestDTO req, Payment payment) {
	        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
	            Document doc = new Document(PageSize.A4, 36, 36, 54, 36);
	            PdfWriter writer = PdfWriter.getInstance(doc, baos);

	            // metadata
	            doc.addAuthor(companyName);
	            doc.addCreationDate();
	            doc.addTitle("Invoice - " + (req.getInvoiceNumber() != null ? req.getInvoiceNumber() : payment.getTransactionId()));
	            doc.open();

	            // Fonts
	            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
	            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
	            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 9);

	            // Header: logo + company info + invoice meta
	            PdfPTable header = new PdfPTable(new float[]{1f, 2f});
	            header.setWidthPercentage(100);

	            // logo cell
	            PdfPCell logoCell = new PdfPCell();
	            logoCell.setBorder(Rectangle.NO_BORDER);
	            if (logoPath != null && !logoPath.isBlank()) {
	                try {
	                    Image logo = Image.getInstance(logoPath);
	                    logo.scaleToFit(100, 60);
	                    logoCell.addElement(logo);
	                } catch (Exception e) {
	                    // If logo fails, keep blank but don't fail invoice generation
	                }
	            } else {
	                // Fallback: company name as title if no logo
	                Paragraph companyTitle = new Paragraph(companyName, titleFont);
	                logoCell.addElement(companyTitle);
	            }
	            header.addCell(logoCell);

	            // company info and invoice metadata
	            PdfPCell infoCell = new PdfPCell();
	            infoCell.setBorder(Rectangle.NO_BORDER);
	            infoCell.addElement(new Paragraph(companyName, bold));
	            infoCell.addElement(new Paragraph(companyAddress, normal));
	            infoCell.addElement(new Paragraph("Email: " + companyEmail, normal));
	            infoCell.addElement(Chunk.NEWLINE);

	            // invoice meta table inside info cell
	            PdfPTable meta = new PdfPTable(2);
	            meta.setWidths(new float[]{1f, 1.2f});
	            meta.setWidthPercentage(80);

	            meta.addCell(makeMetaCell("Invoice No:", bold));
	            meta.addCell(makeMetaCell(req.getInvoiceNumber() != null ? req.getInvoiceNumber() : payment.getTransactionId(), normal));

	            meta.addCell(makeMetaCell("Invoice Date:", bold));
	            meta.addCell(makeMetaCell(DATE_FORMAT.format(new Date()), normal));

	            meta.addCell(makeMetaCell("Transaction ID:", bold));
	            meta.addCell(makeMetaCell(payment.getTransactionId(), normal));

	            meta.addCell(makeMetaCell("Payment Method:", bold));
	            meta.addCell(makeMetaCell(req.getPaymentMethod(), normal));

	            infoCell.addElement(meta);
	            header.addCell(infoCell);

	            doc.add(header);
	            doc.add(Chunk.NEWLINE);

	            // Billing info and customer
	            PdfPTable billing = new PdfPTable(new float[]{1f, 1f});
	            billing.setWidthPercentage(100);

	            PdfPCell billTo = new PdfPCell();
	            billTo.setBorder(Rectangle.NO_BORDER);
	            billTo.addElement(new Paragraph("Bill To:", bold));
	            BillingInfo b = req.getBillingInfo();
	            if (b != null) {
	                billTo.addElement(new Paragraph(b.getName(), normal));
	                billTo.addElement(new Paragraph(b.getEmail(), normal));
	                if (b.getAddressLine1() != null) billTo.addElement(new Paragraph(b.getAddressLine1(), normal));
	                if (b.getAddressLine2() != null) billTo.addElement(new Paragraph(b.getAddressLine2(), normal));
	                String cityLine = (b.getCity() != null ? b.getCity() + ", " : "") + (b.getState() != null ? b.getState() + ", " : "") + (b.getPostalCode() != null ? b.getPostalCode() : "");
	                if (!cityLine.trim().isEmpty()) billTo.addElement(new Paragraph(cityLine, normal));
	                if (b.getCountry() != null) billTo.addElement(new Paragraph(b.getCountry(), normal));
	            }
	            billing.addCell(billTo);

	            // payment summary box
	            PdfPCell paySummary = new PdfPCell();
	            paySummary.setBorder(Rectangle.BOX);
	            paySummary.setPadding(8);
	            paySummary.addElement(new Paragraph("Payment Summary", bold));
	            paySummary.addElement(new Paragraph("Amount: " + formatCurrency(payment.getAmount(), req.getCurrency()), normal));
	            paySummary.addElement(new Paragraph("Status: " + payment.getPaymentStatus(), normal));
	            paySummary.addElement(new Paragraph("Plan ID: " + (payment.getPlanId() == null ? "-" : payment.getPlanId()), normal));
	            paySummary.addElement(new Paragraph("Timestamp: " + (payment.getTimeStamp() != null ? payment.getTimeStamp().toString() : DATE_FORMAT.format(new Date())), normal));
	            billing.addCell(paySummary);

	            doc.add(billing);
	            doc.add(Chunk.NEWLINE);

	            // Items table
	            PdfPTable itemsTable = new PdfPTable(new float[]{4f, 1f, 2f, 2f});
	            itemsTable.setWidthPercentage(100);
	            itemsTable.getDefaultCell().setPadding(6);

	            itemsTable.addCell(makeHeaderCell("Description"));
	            itemsTable.addCell(makeHeaderCell("Qty"));
	            itemsTable.addCell(makeHeaderCell("Unit Price"));
	            itemsTable.addCell(makeHeaderCell("Line Total"));

	            List<InvoiceItem> items = req.getItems();
	            BigDecimal subtotal = BigDecimal.ZERO;
	            if (items != null) {
	                for (InvoiceItem it : items) {
	                    itemsTable.addCell(makeNormalCell(it.getDescription()));
	                    itemsTable.addCell(makeNormalCell(String.valueOf(it.getQuantity())));
	                    itemsTable.addCell(makeNormalCell(formatCurrency(it.getUnitPrice(), req.getCurrency())));
	                    BigDecimal line = it.getLineTotal();
	                    itemsTable.addCell(makeNormalCell(formatCurrency(line, req.getCurrency())));
	                    subtotal = subtotal.add(line);
	                }
	            }

	            // Add subtotal, tax, discount, total as rows merged to right
	            PdfPCell empty = new PdfPCell(new Phrase(""));
	            empty.setColspan(2);
	            empty.setBorder(Rectangle.NO_BORDER);
	            itemsTable.addCell(empty);

	            itemsTable.addCell(makeBoldRightCell("Subtotal"));
	            itemsTable.addCell(makeNormalCell(formatCurrency(subtotal, req.getCurrency())));

	            BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(req.getTaxPercent() / 100.0));
	            itemsTable.addCell(empty);
	            itemsTable.addCell(makeBoldRightCell("Tax (" + req.getTaxPercent() + "%)"));
	            itemsTable.addCell(makeNormalCell(formatCurrency(tax, req.getCurrency())));

	            itemsTable.addCell(empty);
	            itemsTable.addCell(makeBoldRightCell("Discount"));
	            BigDecimal discount = BigDecimal.valueOf(req.getDiscountAmount());
	            itemsTable.addCell(makeNormalCell(formatCurrency(discount, req.getCurrency())));

	            BigDecimal grandTotal = subtotal.add(tax).subtract(discount);
	            itemsTable.addCell(empty);
	            itemsTable.addCell(makeBoldRightCell("Total"));
	            itemsTable.addCell(makeNormalCell(formatCurrency(grandTotal, req.getCurrency())));

	            doc.add(itemsTable);
	            doc.add(Chunk.NEWLINE);

	            // Notes and Thank you
	            if (req.getNotes() != null && !req.getNotes().isBlank()) {
	                Paragraph notes = new Paragraph("Notes: " + req.getNotes(), normal);
	                notes.setSpacingBefore(6);
	                doc.add(notes);
	            }

	            Paragraph thanks = new Paragraph("Thank you for your payment!", bold);
	            thanks.setSpacingBefore(12);
	            doc.add(thanks);

	            // QR code (transaction link or text)
	            try {
	                String qrData = "tx:" + payment.getTransactionId();
	                BarcodeQRCode qrCode = new BarcodeQRCode(qrData, 100, 100, null);
	                Image qrcodeImage = qrCode.getImage();
	                qrcodeImage.scaleToFit(80, 80);
	                qrcodeImage.setAlignment(Element.ALIGN_RIGHT);
	                doc.add(qrcodeImage);
	            } catch (Exception e) {
	                // ignore QR generation failure
	            }

	            doc.close();
	            writer.close();
	            return baos.toByteArray();
	        } catch (Exception e) {
	            throw new RuntimeException("Failed to generate invoice PDF", e);
	        }
	    }

	    /** Save the invoice bytes to disk (optional helper) */
	    public void saveInvoiceToFile(byte[] pdfBytes, String outputPath) {
	        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
	            fos.write(pdfBytes);
	        } catch (Exception e) {
	            throw new RuntimeException("Unable to save invoice to file", e);
	        }
	    }

	    /* ----------------- Helper methods for table cells and formatting ----------------- */

	    private PdfPCell makeMetaCell(String text, Font font) {
	        PdfPCell c = new PdfPCell(new Phrase(text, font));
	        c.setBorder(Rectangle.NO_BORDER);
	        return c;
	    }

	    private PdfPCell makeHeaderCell(String text) {
	        PdfPCell c = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
	        c.setHorizontalAlignment(Element.ALIGN_CENTER);
	        c.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        return c;
	    }

	    private PdfPCell makeNormalCell(String text) {
	        PdfPCell c = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 9)));
	        c.setPadding(6);
	        return c;
	    }

	    private PdfPCell makeBoldRightCell(String text) {
	        PdfPCell c = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
	        c.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        c.setBorder(Rectangle.NO_BORDER);
	        return c;
	    }

	    private String formatCurrency(Number number, String currency) {
	        if (number == null) return "-";
	        if (number instanceof BigDecimal) {
	            return (currency == null ? "" : currency + " ") + ((BigDecimal) number).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	        } else {
	            return (currency == null ? "" : currency + " ") + String.format("%.2f", number.doubleValue());
	        }
	    }
	}



