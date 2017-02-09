package com.agilityio.todo.security.jwt;

import com.agilityio.todo.domain.User;
import com.agilityio.todo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Project: toto-app
 * Token Authentication Service
 */
@Component
public class TokenAuthenticationService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.headerString}")
    private String headerString;

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Autowired
    UserService userService;

    public void addAuthentication(HttpServletResponse response, User user) {
        Claims claims = Jwts.claims().setSubject(user.getUserName());
        claims.put("userId", user.getUserId() + "");

        String JWT = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        response.addHeader(headerString, tokenPrefix + " " + JWT);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(headerString);
        token = prepareToken(token);
        if (token != null) {
            try {
                String userName = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
                if (userName != null && !isTokenExpired(token) && userService.findUsersByUserName(userName) != null) {
                    return new AuthenticatedUser(userName);
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

    public User getUserFromToken(HttpServletRequest request) {
        String token = request.getHeader(headerString);
        token = prepareToken(token);
        User user = new User();
        try {
            final Claims claims = getClaimsFromToken(token);
            user.setUserId(Long.parseLong((String) claims.get("userId")));
            user.setUserName(claims.getSubject());
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    private Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return null;
        }
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

    private String prepareToken(String token) {
        if (token != null) {
            return token.substring(7);
        }
        return token;
    }
}
