package com.youngadessi.demo.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {


    public String createToken(String userId,String username ){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 120000);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, "secret")
                .claim("username", username)
                .compact();

    }

    public void validateToken(String token){
        Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username").toString();
    }
}
