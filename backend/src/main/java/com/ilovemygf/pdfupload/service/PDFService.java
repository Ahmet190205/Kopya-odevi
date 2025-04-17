/*
 * Interface. PDFService icin.
 * 
 * Author: Ahmet
 * Son Guncelleme: 10.02.2025
 * TODO: --
*/

package com.ilovemygf.pdfupload.service;

public interface PDFService { // PdfServiceImpl icin interface
    void processPdf(byte[] pdfData, String username);
}