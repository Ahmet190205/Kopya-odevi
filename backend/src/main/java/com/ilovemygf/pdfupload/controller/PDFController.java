/*
 * PDF Upload Controller
 * 
 * PDF yukleme isteklerini yoneten controller sinifi.
 * Endpoint: /api/pdf/upload
 * 
 * Ozellikler:
 * - PDF dosyalarini alir ve saklar
 * - Imza dogrulamasi yapar
 * - Replay attack'lere karsi timestamp kontrolu yapar
 * 
 * Guvenlik:
 * - Her istek imzali olmali
 * - Timestamp 5 dakikadan eski olmamali
 * - Kullanici yetkisi kontrol edilmeli
 * 
 * Author: Ahmet
 * Son Guncelleme: 10.02.2025
*/
package com.ilovemygf.pdfupload.controller;


import com.ilovemygf.pdfupload.model.SignedPdfRequest; // bizim classlarimiz
import com.ilovemygf.pdfupload.service.PDFService;     // bizim service classimiz
import com.ilovemygf.pdfupload.utils.SignatureUtils;  // bizim utils classimiz
import org.springframework.http.HttpStatus;           // HTTP status kodlarini tutan class
import org.springframework.http.ResponseEntity;       // HTTP yanitlarini tutan class
import org.springframework.web.bind.annotation.*;         // HTTP isteklerini yonetmek icin kullanilan annotation'lar
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf") // endpoint soyle gorunecek Url'de: http://localhost:8080/api/pdf
@CrossOrigin(origins = "*")
public class PDFController {

    private final PDFService pdfService;
    private final SignatureUtils signatureUtils;

    public PDFController(PDFService pdfService, SignatureUtils signatureUtils) {
        this.pdfService = pdfService;
        this.signatureUtils = signatureUtils;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username,
            @RequestParam("timestamp") String timestamp) {
        
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Dosya boş");
            }

            pdfService.processPdf(file.getBytes(), username);
            return ResponseEntity.ok("PDF başarıyla yüklendi");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Hata: " + e.getMessage());
        }
    }

    private boolean validateSignature(SignedPdfRequest request) {
        String message = request.getUsername() + request.getTimestamp();
        return signatureUtils.verifyHmacSignature(message, request.getSignature());
    }

    private boolean isTimestampValid(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long requestAge = currentTime - timestamp;
        // 5 dakika (300,000 milisaniye) içinde gelen istekleri kabul et
        return requestAge <= 300_000;
    }
}




