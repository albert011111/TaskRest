package com.kruczek.security.jwt;

import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.kruczek.auth.services.UserPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

@Component
public class JwtProvider {
    private static final Logger LOGGER = Logger.getLogger(JwtProvider.class.getName());

	@Value(value = "${kruczek.com.jwtSecret}")
    private String jwtSecret;

    @Value(value = "${kruczek.com.jwtExp}")
    private long jwtExpiration;

    public String createJwtToken(final Authentication authentication) {
        Objects.requireNonNull(authentication, getNpeDescription("authentication"));
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

	boolean validateToken(final String tokenToValidate) {
        Objects.requireNonNull(tokenToValidate, getNpeDescription("tokenToValidate"));

        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(tokenToValidate);
            return true;
        } catch (ExpiredJwtException
                | IllegalArgumentException
                | SignatureException
                | MalformedJwtException
                | UnsupportedJwtException e) {
            LOGGER.log(Level.WARNING, "Invalid JWT Token: " + e.getMessage());
        }
        return false;
    }

	String getUsernameFromJwtToken(final String token) {
        Objects.requireNonNull(token, getNpeDescription("token"));

        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}



