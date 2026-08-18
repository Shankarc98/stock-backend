package com.example.stock_backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.jwk.source.ImmutableSecret;


@Service
public class JwtService {
    
    private final JwtEncoder jwtEncoder; 

    public JwtService(@Value("${jwt.secret}") String secret){

        byte [] keyBytes = Base64.getDecoder().decode(secret); 

        SecretKey key = new SecretKeySpec(keyBytes, "HmacSHA256");

        this.jwtEncoder = new NimbusJwtEncoder(new ImmutableSecret<>(key));

    }

    public String generateToken(String username){
        Instant now = Instant.now(); 

        JwtClaimsSet claims = JwtClaimsSet.builder()
                    .subject(username)
                    .issuedAt(now)
                    .expiresAt(now.plus(1, ChronoUnit.HOURS))
                    .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder
            .encode(
                JwtEncoderParameters.from(header, claims)
            )
            .getTokenValue();
    }
}
