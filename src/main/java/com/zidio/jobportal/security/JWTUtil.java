package com.zidio.jobportal.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

    // Secret key for signing the JWT
    private final String jwtSecret = "secretKey"; 
    
    // Token validity: 1 day (in milliseconds)
    private final long jwtExpiration = 86400000L;

    /**
     * Generate JWT token for authenticated user
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();  // get logged-in username
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration); // correct: add milliseconds

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Get username from token
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Invalid token
            return false;
        }
    }
}
