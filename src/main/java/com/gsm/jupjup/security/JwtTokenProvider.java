package com.gsm.jupjup.security;

// import 생략

import com.gsm.jupjup.model.Admin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final CustomUserDetailService customUserDetailService;

//    public final static long TOKEN_VALIDATION_SECOND = 1000L * 86400;  //하루를 accessToken 만료 기간으로 잡는다
//    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 3600 * 24 * 210; //7개월을 refreshToken 만료 기간으로 잡는다.

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;  //  1분을 accessToken 만료 기간으로 잡는다
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 3600; // 1시간을 refreshToken 만료 기간으로 잡는다.

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    // Base64 encoded secret key
    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserEmail(String token){
        return extractAllClaims(token).get("userEmail", String.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(Admin admin) {
        return doGenerateToken(admin.getEmail(), admin.getRoles(), admin.getClassNumber(), admin.getName(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(Admin admin) {
        return doGenerateToken(admin.getEmail(), admin.getRoles(), admin.getClassNumber(), admin.getName(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String doGenerateToken(String userEmail, List<String> roles, String classNumber, String userName, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("userEmail", userEmail);
        claims.put("classNumber", classNumber);
        claims.put("userName", userName);
        claims.put("roles", roles);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
        return jwt;
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


    public String resolveToken(HttpServletRequest req){
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return  bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}