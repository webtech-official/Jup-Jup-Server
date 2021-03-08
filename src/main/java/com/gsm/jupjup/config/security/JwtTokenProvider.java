package com.gsm.jupjup.config.security;

// import 생략

import com.gsm.jupjup.model.Admin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider { // JWT 토큰을 생성 및 검증 모듈

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 86400;  //하루를 accessToken 만료 기간으로 잡는다
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 3600 * 24 * 210; //7개월을 refreshToken 만료 기간으로 잡는다.

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰을 가져오기 위한 정보(Claims) 등록
     * @param token 토큰
     * @return Claims
     * @throws ExpiredJwtException 에러 처리
     */
    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    /**
     * 사용자 정보 가져오기
     * @param token 토큰
     * @return UserEmail
     */
    public String getUserName(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    /**
     * 토큰 만료 검사
     * @param token 토큰
     * @return Boolean
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    /**
     * Access Token 생성
     * @param admin 회원 정보
     * @return Access Token
     */
    public String generateToken(Admin admin) {
        return doGenerateToken(admin.getUsername(), TOKEN_VALIDATION_SECOND);
    }

    /**
     * Refresh Token 생성
     * @param admin 회원 정보
     * @return Refresh Token
     */
    public String generateRefreshToken(Admin admin) {
        return doGenerateToken(admin.getUsername(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    /**
     * 토큰 생성
     * @param username UserEmail
     * @param expireTime 만료 시간
     * @return Token
     */
    public String doGenerateToken(String username, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("username", username);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }

    /**
     * 토큰 만료 검사 (최종)
     * @param token 토큰
     * @param userDetails userDetails
     * @return Boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}