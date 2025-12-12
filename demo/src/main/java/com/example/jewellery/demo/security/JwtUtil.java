package com.example.jewellery.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.jewellery.demo.model.User;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class JwtUtil {
@Value("${jwt.secret}")
private String jwtSecret;


@Value("${jwt.expirationMs:86400000}")
private long jwtExpirationMs;


private SecretKey getKey() {
return Keys.hmacShaKeyFor(jwtSecret.getBytes());
}


public String generateToken(User user) {
Map<String, Object> claims = new HashMap<>();
claims.put("roles", user.getRoles());
return Jwts.builder()
.setClaims(claims)
.setSubject(user.getUsername())
.setIssuedAt(new Date())
.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
.signWith(getKey(), SignatureAlgorithm.HS256)
.compact();
}


public boolean validateToken(String token) {
try {
Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
return true;
} catch (Exception ex) { return false; }
}


public String getUsernameFromToken(String token) {
Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
return claims.getSubject();
}


public List<SimpleGrantedAuthority> getAuthorities(String token) {
Claims claims = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
List<String> roles = (List<String>) claims.get("roles");
if (roles == null) return Collections.emptyList();
return roles.stream().map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
}
}