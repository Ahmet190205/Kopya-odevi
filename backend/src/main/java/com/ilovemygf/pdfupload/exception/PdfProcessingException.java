/*
 * PDF Upload Exception
 * Hatalar icin exception sinifi.
 * Yeterince basit, ellemeyin.
 * 
 * Author: Ahmet
 * Son Guncelleme: 10.02.2025
*/

package com.ilovemygf.pdfupload.exception;

public class PdfProcessingException extends RuntimeException {
    public PdfProcessingException(String message) {
        super(message);
    }

    public PdfProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
} 