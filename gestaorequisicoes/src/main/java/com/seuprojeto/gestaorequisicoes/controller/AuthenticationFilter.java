package com.seuprojeto.gestaorequisicoes.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String IV = "AAAAAAAAAAAAAAAA";
    private static final String encryptionKey = "0123456789abcdef";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Permitir acesso ao endpoint de login sem autenticação
        if ("/login".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar o cookie de autenticação para outros endpoints
        String authCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "DESWEB".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> decrypt(cookie.getValue()))
                .orElse(null);

        if (authCookie != null && validateSession(authCookie)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Não autorizado");
            response.getWriter().flush();
        }
    }

    private boolean validateSession(String sessionData) {
        // Implemente a validação de acordo com as regras do negócio
        return true;
    }

    private String decrypt(String encryptedData) {
        try {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes()));
            return new String(cipher.doFinal(decodedData));
        } catch (Exception e) {
            return null;
        }
    }
}
