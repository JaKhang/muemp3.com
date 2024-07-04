package com.mue.security.services;



import com.mue.security.config.SecurityProperties;
import com.mue.security.domain.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DefaultJwtProvider implements JwtProvider {

    private final SecurityProperties authProperties;

    @Override
    public UUID extractUserID(String jwt) {
        return UUID.fromString(extractClaim(jwt, Claims::getSubject));
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(Authentication authentication) {
        return generateToken(new HashMap<>(), authentication);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        long expiredTime = authProperties.getJwt().getExpiredTime() * System.currentTimeMillis() + 1000L * 60;
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userPrincipal.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expiredTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserPrincipal userPrincipal) {
        final UUID userID = extractUserID(token);
        return (Objects.equals(userPrincipal.getId(), userID) && !isTokenExpired(token));
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(authProperties.getJwt().getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
