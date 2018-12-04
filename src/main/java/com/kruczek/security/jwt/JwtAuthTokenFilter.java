package com.kruczek.security.jwt;

import com.kruczek.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

    @Autowired
    JwtProvider tokenProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = retrieveJwtToken(httpServletRequest);
            if (jwtToken != null && tokenProvider.validateToken(jwtToken)) {
                //wyciaga informacje o userze
                String tokenUsername = tokenProvider.getUsernameFromJwtToken(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(tokenUsername);

                //tworzy JWT Token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                //przechowuje obiekt autentykacji w SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception ex) {
            LOGGER.error("Cant set user authentication. Msg: {}", ex);
        }
    }

    private String retrieveJwtToken(HttpServletRequest httpRequest) {
        String header = httpRequest.getHeader("AuthorizationPatryk");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }
}
