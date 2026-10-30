package com.example.stock_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.example.stock_backend.config.SecurityConfig;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

class JwtServiceTest {
    private static final String SECRET = Base64.getEncoder().encodeToString(new byte[32]);

    @Test
    void issuesTokensWithTwentyFourHourLifetime(){
        Instant beforeLogin = Instant.now();
        String token = new JwtService(SECRET).generateToken("player");
        Instant afterLogin = Instant.now();

        var decoded = decoder().decode(token);

        assertEquals("player", decoded.getSubject());
        assertEquals(Duration.ofHours(24), Duration.between(decoded.getIssuedAt(), decoded.getExpiresAt()));
        assertTrue(!decoded.getIssuedAt().isBefore(beforeLogin.minusSeconds(1)));
        assertTrue(!decoded.getIssuedAt().isAfter(afterLogin.plusSeconds(1)));
    }

    @Test
    void rejectsExpiredTokensWithoutGracePeriod(){
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");
        JwtEncoder encoder = new NimbusJwtEncoder(new ImmutableSecret<>(key));
        Instant now = Instant.now();
        JwtClaimsSet expiredClaims = JwtClaimsSet.builder()
            .subject("player")
            .issuedAt(now.minus(Duration.ofHours(25)))
            .expiresAt(now.minusSeconds(1))
            .build();
        String expiredToken = encoder.encode(JwtEncoderParameters.from(
            JwsHeader.with(MacAlgorithm.HS256).build(),
            expiredClaims
        )).getTokenValue();

        assertThrows(JwtException.class, () -> decoder().decode(expiredToken));
    }

    private JwtDecoder decoder(){
        return new SecurityConfig().jwtDecoder(SECRET);
    }
}
