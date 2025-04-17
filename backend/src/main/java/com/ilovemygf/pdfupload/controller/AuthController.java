/*
 * Kullanici login oldugunda JWT tokeni veriyor
 * 
 * Author: Ahmet
 * Son Guncelleme: 10.02.2025
 * TODO: 
 * 1. UserService eklenecek
 *    - Veritabani baglantisi
 *    - PostgreSQL eklemeyi planliyorum.
 *    - Ancak kullanicilarin veritabaninda olmasi gerekiyor.
 *    - Bunu yapmaya deger mi?
 *    - Projemizin kapsam alaninin disinda, belki sqlLite kullanilabilir.
*/

package com.ilovemygf.pdfupload.controller;



import com.ilovemygf.pdfupload.model.LoginRequest;
import com.ilovemygf.pdfupload.model.LoginResponse;
import com.ilovemygf.pdfupload.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        
        return ResponseEntity.ok(response);
    }
}