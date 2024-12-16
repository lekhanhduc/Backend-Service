package vn.khanhduc.backendservice.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import vn.khanhduc.backendservice.common.TokenType;
import vn.khanhduc.backendservice.service.JwtService;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import static vn.khanhduc.backendservice.common.TokenType.ACCESS_TOKEN;
import static vn.khanhduc.backendservice.common.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "JWT_SERVICE")
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiryMinutes}")
    private long expiryMinutes;

    @Value("${jwt.expiryDay}")
    private long expiryDay;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    @Override
    public String generateAccessToken( String email, List<String> authorities) {
        log.info("Generate AccessToken");

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", authorities);
        return generateToken(claims, email);
    }

    @Override
    public String generateRefreshToken( String email, List<String> authorities) {
        log.info("Generate RefreshToken");

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", authorities);
        return generateRefreshToken(claims, email);
    }

    @Override
    public String extractUsername(String token, TokenType tokenType) {
        log.info("Extract Username");
        return extractClaims(tokenType, token, (Claims::getSubject));
    }

    private <T>  T extractClaims(TokenType tokenType, String token, Function<Claims, T> claimsFunc) {
        final Claims claims = extractAllClaim(token, tokenType);
        return claimsFunc.apply(claims);
    }

    private Claims extractAllClaim(String token, TokenType tokenType) {
        try {
            return Jwts.parser().setSigningKey(accessKey).parseClaimsJws(token).getBody();
        }catch (SignatureException | ExpiredJwtException e) {
            log.info(e.getMessage());
            throw new AccessDeniedException("Access dined!, error "+e.getMessage());
        }
    }

    private String generateToken(Map<String, Object> claims, String email) {
        log.info("Generate Access Token");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expiryMinutes))
                .signWith(getKey(ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, String email) {
        log.info("Generate Refresh Token");

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryDay))
                .signWith(getKey(REFRESH_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType tokenType) {
        switch (tokenType) {
            case ACCESS_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
            }
            case REFRESH_TOKEN -> {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
            }
            default -> throw new IllegalStateException("Invalid Token type");
        }
    }
}
