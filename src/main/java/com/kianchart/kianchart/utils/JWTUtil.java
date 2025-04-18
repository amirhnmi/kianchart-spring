package com.kianchart.kianchart.utils;

import com.kianchart.kianchart.config.EnvConfig;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtil {
//    private final String SECRET_KEY = EnvConfig.getSECRET_KEY();
////    private final int EXPIRE_TIME = EnvConfig.getEXPIRE_TIME();
//
//    public String generateAccessToken(String username,Long userId, List<String> scopes){
//        return Jwts.builder().setSubject(username)
//                .setIssuer(EnvConfig.getAPP_NAME())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
//                .claim("scopes",scopes)
//                .claim("user_id",userId)
//                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
//                .compact();
//    }
//
//    public Claims extractAllClaims(String token){
//        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
//    }
//
//    public String getUsername(String token){
//        return extractAllClaims(token).getSubject();
//    }
//
//    public List<String> getScopes(String token){
//        Object scopes = extractAllClaims(token).get("scopes");
//        if (scopes instanceof List<?>) {
//            return ((List<?>) scopes).stream()
//                    .map(Object::toString)
//                    .collect(Collectors.toList());
//        }
//        return List.of();
//    }
//
//    public Long getUserId(String token){
//        return extractAllClaims(token).get("user_id",Long.class);
//    }
}
