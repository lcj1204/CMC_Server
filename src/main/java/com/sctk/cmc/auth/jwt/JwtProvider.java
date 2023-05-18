package com.sctk.cmc.auth.jwt;

import com.sctk.cmc.auth.domain.Token;
import com.sctk.cmc.auth.dto.CommonUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String PREFIX_TOKEN = "Bearer ";

    @Value("${jwt.token.secret-key}")
    private String secretKey;
    @Value("${jwt.token.access-token.expiredTimeMs}")
    private Long accessTokenExpiredTimeMs;
    @Value("${jwt.token.refresh-token.expiredTimeMs}")
    private Long refreshTokenExpiredTimeMs;

    public Token generateToken(CommonUser user) {
        return Token.builder()
                .accessToken( generateAccessToken(user) )
                .refreshToken( generateRefreshToken() )
                .build();
    }

    public String generateAccessToken(CommonUser user) {
        Claims claims = Jwts.claims(); // 일종의 map.
        claims.put("email", user.getEmail()); // 어떤 정보를 넣고 싶을 때, 이 claims 에 넣음

        Date now = new Date();  // 생성 날짜
        Date expired = new Date(now.getTime() + accessTokenExpiredTimeMs);   // 만료 날짜

        return Jwts.builder()
                .setClaims( claims )
                .setAudience( user.getRole() )
                .setSubject("access_token")
                .setIssuedAt( now )
                .setExpiration( expired )
                .signWith( SignatureAlgorithm.HS256, secretKey ) // SignatureAlgorithm.HS256 를 이용해서 key를 암호화(sign) 함.
                .compact();
    }

    public String generateRefreshToken() {
        Claims claims = Jwts.claims();

        Date now = new Date();
        Date expired = new Date(now.getTime() + refreshTokenExpiredTimeMs);

        return Jwts.builder()
                .setClaims( claims )
                .setSubject("refresh_token")
                .setIssuedAt( now )
                .setExpiration( expired )
                .signWith( SignatureAlgorithm.HS256, secretKey )
                .compact();
    }

    public boolean isValidToken(String token) {
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            log.info("expiredDate={}", claimsJws.getBody().getExpiration());
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            log.info("이미 만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰 형식입니다.");
        } catch (MalformedJwtException e) {
            log.info("인증 토큰이 올바르게 구성되지 않았습니다.");
        } catch (SignatureException e) {
            log.info("인증 시그니처가 올바르지 않습니다.");
        } catch (IllegalArgumentException e) {
            log.info("잘못된 토큰입니다.");
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(PREFIX_TOKEN)) {
            return bearerToken.substring(PREFIX_TOKEN.length());
        }
        return null;
    }

    public CommonUser getPayload(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody();

        return CommonUser.builder()
                .role(claims.getAudience())
                .email(claims.get("email", String.class))
                .build();
    }

    public Date getExpiredTimeMs(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody().getExpiration();
    }
}

