/*
 * Login Request
 * 
 * Kullanici giris bilgilerini tutan class.
 * 
 * Author: Ahmet
 * Son Guncelleme: 10.02.2025
*/

package com.ilovemygf.pdfupload.model;

public class LoginRequest {
    

    private String username;  // Kullanıcı adı
    private String password;  // Kullanıcı şifresi

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter ve Setter Metotları
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}