package com.seuprojeto.gestaorequisicoes.controller;

import com.seuprojeto.gestaorequisicoes.model.Usuario;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@RestController
public class LoginController {

    private static final String IV = "AAAAAAAAAAAAAAAA";
    private static final String encryptionKey = "0123456789abcdef";
    private static final int TIMEOUT_IN_SECONDS = 600;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String conta) throws Exception {
        Usuario usuario = new Usuario(conta);
        Gson gson = new Gson();
        String authData = gson.toJson(usuario);

        byte[] encryptedData = encrypt(authData);
        String encodedData = Base64.getEncoder().encodeToString(encryptedData);

        ResponseCookie cookie = ResponseCookie.from("DESWEB", encodedData)
                .path("/")
                .httpOnly(true)
                .maxAge(TIMEOUT_IN_SECONDS)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().headers(headers).body("Login da conta '" + conta + "' feito com sucesso!");
    }

    private byte[] encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
        return cipher.doFinal(text.getBytes());
    }
}
