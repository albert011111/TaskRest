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

@Component //typ stereotypu w Springu.
// Adnotacja ta mówi, że obiekt tej klasy ma być zarządzany przez Springa jako BEAN
// - automatycznie wyszukiwana przez kontener
// UWAGA!! @Component == <bean> z konfiguracji XMLowej !!
//wszystkie inne stereotypu dziedziczą po @Component
public class JwtProvider {
    private static final Logger LOGGER = Logger.getLogger(JwtProvider.class.getName());

    @Value(value = "${kruczek.com.jwtSecret}")//adnotacja przypisuje domyślną wartość dla propertisa,
    // w tym przypadku te wartości zapisane będą w application.properties
    private String jwtSecret;

    @Value(value = "${kruczek.com.jwtExp}")
    private long jwtExpiration;

    /*Authentication - interfejs
     Reprezentuje TOKEN dla requesta uwierzytelnienia
     lub reprezentuje TOKEN dla uwierzytelnionego użytkownika po przetworzeniu requesta
      przez metodę AuthenticationManager.authenticate(Authentication)
     */
    public String createJwtToken(final Authentication authentication) {
        Objects.requireNonNull(authentication, getNpeDescription("authentication"));
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        //Jwts --> fabryka dla tokenow JWT.
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) //sub(subject) - CLAIMS, nie trzeba ale dobrze to dodawac (Registred claims)
                .setIssuedAt(new Date()) //okresla, kiedy token JWT zostal utworzony (timestamp - znak czasu)
                .setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000)) // nie trzeba tlumaczyc
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // "podpisuje" JWT
                .compact();
    }

    public boolean validateToken(final String tokenToValidate) {
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

    public String getUsernameFromJwtToken(final String token) {
        Objects.requireNonNull(token, getNpeDescription("token"));

        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}



