package com.kruczek.security.jwt;

import com.kruczek.services.UserPrincipal;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    //TODO sprawdzić, czy tak wprowadzone dane również działają...
//    @Value(value = "${kruczek.com.jwtExp}")
    @Value(value = "8400")
    private long jwtExpiration;

    /*Authentication - interfejs
     Reprezentuje TOKEN dla requesta uwierzytelnienia
     lub reprezentuje TOKEN dla uwierzytelnionego użytkownika po przetworzeniu requesta
      przez metodę AuthenticationManager.authenticate(Authentication)
     */
    public String createJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        //Jwts --> fabryka dla tokenow JWT.
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) //sub(subject) - CLAIMS, nie trzeba ale dobrze to dodawac (Registred claims)
                .setIssuedAt(new Date()) //okresla, kiedy token JWT zostal utworzony (timestamp - znak czasu)
                .setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000)) // nie trzeba tlumaczyc
                .signWith(SignatureAlgorithm.ES512, jwtSecret) // "podpisuje" JWT
                .compact();
    }

    public boolean validateToken(String tokenToValidate) {
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

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }


}



