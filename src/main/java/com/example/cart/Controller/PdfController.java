package com.example.cart.Controller;

import com.example.cart.Model.ProductModel;
import com.example.cart.Repository.ProductRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPdf() {

        try {
            // ✅ Fetch all products
            List<ProductModel> products = productRepository.findAll();

            // ✅ Setup PDF document
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("Product Report"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // ✅ Create Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            table.addCell("ID");
            table.addCell("Name");
            table.addCell("Price");
            table.addCell("Quantity");

            // ✅ Populate Table
            for (ProductModel p : products) {
                table.addCell(String.valueOf(p.getId()));
                table.addCell(p.getName());
                table.addCell(String.valueOf(p.getPrice()));
                table.addCell(String.valueOf(p.getQuantity()));
            }

            document.add(table);
            document.close(); // ✅ CRITICAL

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(out.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
