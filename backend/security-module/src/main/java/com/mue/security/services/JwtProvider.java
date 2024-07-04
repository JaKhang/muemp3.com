package com.mue.security.services;

import com.mue.security.domain.UserPrincipal;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public interface JwtProvider {
    UUID extractUserID(String jwt);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(Authentication authentication);

    String generateToken(Map<String, Object> extraClaims, Authentication authentication);
    boolean isTokenValid(String jwt, UserPrincipal userPrincipal);

}
