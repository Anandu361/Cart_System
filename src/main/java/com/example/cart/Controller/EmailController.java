package com.example.cart.Controller;

import com.example.cart.Model.ProductModel;
import com.example.cart.Repository.ProductRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.*;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/send")
    public ResponseEntity<String> sendEmailWithPdf() {

        try {
            List<ProductModel> products = productRepository.findAll();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Product Report"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Price");
            table.addCell("Quantity");

            for (ProductModel p : products) {
                table.addCell(String.valueOf(p.getId()));
                table.addCell(p.getName());
                table.addCell(String.valueOf(p.getPrice()));
                table.addCell(String.valueOf(p.getQuantity()));
            }

            document.add(table);
            document.close();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("test@mailtrap.io");  
            helper.setSubject("Product Report PDF");
            helper.setText("Please find attached the product report.");

            ByteArrayResource file = new ByteArrayResource(out.toByteArray());
            helper.addAttachment("products.pdf", file);

            mailSender.send(message);

            return ResponseEntity.ok("Email sent successfully");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Email sending failed");
        }
    }
}
