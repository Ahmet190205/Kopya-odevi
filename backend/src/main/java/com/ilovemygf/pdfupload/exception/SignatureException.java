/*
 * Signature Exception
 * 
 * Hatalar icin exception sinifi.
 * 
 * Author: Ahmet
 * Son Guncelleme: 10.02.2025
*/

package com.ilovemygf.pdfupload.exception;

public class SignatureException extends RuntimeException {
    public SignatureException(String message) {
        super(message);
    }

    public SignatureException(String message, Throwable cause) {
        super(message, cause);
    }
} 