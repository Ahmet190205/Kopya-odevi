/*
 * Login Response
 * 
 * Client'a token gonderir.
 * 
 * Author: Ahmet
 * Son Guncelleme: 10.02.2025
*/

package com.ilovemygf.pdfupload.model;

public class LoginResponse {


    private String token;  // Kullanıcının oturum açması için gerekli token

    public LoginResponse() {}

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter ve Setter
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}