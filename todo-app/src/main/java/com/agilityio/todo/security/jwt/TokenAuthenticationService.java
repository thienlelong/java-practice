package com.agilityio.todo.security.jwt;

import com.agilityio.todo.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Project: toto-app
 * Token Authentication Service
 */
public class TokenAuthenticationService {
    private long EXPIRATIONTIME = 1000 * 60 * 60; // 1 h
    private String secret = "TodoSecret";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";

    public void addAuthentication(HttpServletResponse response, String userName) {
        String JWT = Jwts.builder()
                .setSubject(userName)
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
                if (username != null && !isTokenExpired(token.substring(7))) {
                    return new AuthenticatedUser(username);
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token.substring(7));
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Boolean validateToken(String token, User user) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(user.getUserName())
                        && !isTokenExpired(token));
    }
}
