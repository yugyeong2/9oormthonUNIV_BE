package org.be._9oormthonuniv.domain.user.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    // 시크릿 키 주입
    @Value("${jwt.secret}")
    private String secretKey;

    // 토큰 유효 기간 (밀리초)
    @Value("${jwt.expiration}")
    private long expiration;

    // JWT 토큰 생성 메서드
    public String generateToken(String username) {
        Date now = new Date(); // 현재 시간
        Date expiry = new Date(now.getTime() + expiration); // 만료 시간

        return Jwts.builder()
                .setSubject(username)                           // 사용자 식별값 (username)
                .setIssuedAt(now)                               // 토큰 발급 시간
                .setExpiration(expiry)                          // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 서명 알고리즘 + 비밀키
                .compact();                                     // 최종 JWT 문자열 생성
    }

    // 토큰에서 사용자 이름 추출
    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // subject 필드가 username
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token); // 파싱이 성공하면 유효한 토큰
            return true;
        } catch (JwtException e) {
            // 토큰이 위조되었거나 만료되었을 때 예외 발생
            return false;
        }
    }
}
