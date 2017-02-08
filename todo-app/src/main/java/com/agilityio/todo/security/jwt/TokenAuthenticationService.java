package com.agilityio.todo.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * Project: toto-app
 * Token Authentication Service
 */
public class TokenAuthenticationService {
    private long EXPIRATIONTIME = 1000 * 60 * 60 * 24; // 1 days
    private String secret = "TodoSecret";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) {
        // We generate a token now.
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        response.addHeader(headerString, tokenPrefix + " " + JWT);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(headerString);
        if (token != null) {
            try {
                String username = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token.substring(7))
                        .getBody()
                        .getSubject();
                if (username != null) {
                    return new AuthenticatedUser(username);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
