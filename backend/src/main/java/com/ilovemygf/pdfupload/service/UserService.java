/*
 * Kullanicilari maymun gibi elle giriyoruz.
 * Bunun yerine, database'den alinmasi gerekiyor.
 * Complexity discretion analizi yapilabilir.
 * Eger database ekleyeceksem Elasticsearch kullanip ekstra bir servis yazmak istiyorum.
 * Ancak ucuncu parti servis karisinda pek bir etkisi olmayabilir.
 * Daha sonra karar verecegim.
 * 
 * Author: Ahmet
 * Son Guncelleme: 17.04.2025
*/
package com.ilovemygf.pdfupload.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final Map<String, String> users = new HashMap<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService() {
        // Kullanicilari elle buraya ekliyoruz. 
        // GrantedAuthority list ile buraya OGRETMEN / OGRENCI rolleri ekleyebilirsiniz
        // User.builder() bkz: Springboot kullanimi da mumkun
        users.put("ahmet", passwordEncoder.encode("ahmet123"));
        users.put("mehmet", passwordEncoder.encode("mehmet123"));
        users.put("ayse", passwordEncoder.encode("ayse123"));


        // burada ogretmen rolu eklendiginde, iki temel sey yapmak gerekyior
        // oncelikle ANALIZBASLAT endpointi olusturulmali
        // requestmapping ile /api/ogretmen seklinde bi endpoint tanimla bkz: benim kodladigim endpoint mantigi
        // postmapping ve getmapping olusturmasi lazim
        // postmapping id lere gore pdfleri alabilir
        // PDFImpl kisminda gerekli mantigi kodlamaniz gerekiyor
        // get ise oldukca acik olmali
        // bkz: JwtService.java
        // Roller zaten userDetails.getAuthorities() ile geliyor
        // authorities kismidna ["OGRENCI_ROL", "OGRETMEN_ROL"] gibi bir liste olustur
        // payloada bunlari ekle
        // jwts builderda claimlerle birlikte bunu döndür
        // kisa ipucu: endpoint yazarken auth etmeden once (ornek olarak ogretmen icin)
        // @PreAuthorize("hasRole('OGRETMEN_ROL')") ile bunu yapabilirsin
        // sanirim SecurityConfig.java'ya gidip bir de son olarak
        // authorizeHttpRequests kismina requestMatchers("/api/ogrenci/**").hasRole("OGRENCI_ROL") vs vs eklemen lazim
        // sanirim bir ipucu daha verebilirim
        // asagida loadUserByUsername kismi var
        // List<GrantedAuthority> authorities diye bir liste olustur
        // switch case ile butun usernamelere bir rol atayabilirsin, boylece kimler ogretmen kimler ogrenci olarak kayitli bakabilirsin
        // switch (username) {
        //  case "ali":     yield List.of(new SimpleGrantedAuthority("OGRENCI_ROL"));
        //  case "hasan": yield List.of(new SimpleGrantedAuthority("OGRETMEN_ROL"));
        //  default:        yield List.of();
        // };
        // bu dedilerimi yazmak icin her seyi zaten sagladim, yazdigim kodlara ornek kullanimlar icin basvurabilirsin
        // Springsecurity ve Springboot dokumantasyonlarini internetten bulabilirsin
        // zaten sadece cok basit authantication metotlarina ihtiyacin var, olabildigince basit tutmaya calis
        // frontend icin iki secenek var ilki Thymleaf veya JSP
        // diger ise JWT’de OGRETMEN_ROL var mi ona bakmak
        // decodeJwt ile once JWT decode edilebilir (npm paket)
        // daha sonra payload'da rol var mi bakilir

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String encodedPassword = users.get(username);
        if (encodedPassword == null) {
            throw new UsernameNotFoundException("Kullanici bulunamadi: " + username);
        }
        return new User(username, encodedPassword, new ArrayList<>());
    }
} 