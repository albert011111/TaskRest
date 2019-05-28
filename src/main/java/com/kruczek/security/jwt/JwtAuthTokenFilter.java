package com.kruczek.security.jwt;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kruczek.auth.services.UserDetailsServiceImpl;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

public class JwtAuthTokenFilter extends OncePerRequestFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthTokenFilter.class);

	private final JwtProvider tokenProvider;
	private final UserDetailsServiceImpl userDetailsService;

	@Autowired
	public JwtAuthTokenFilter(JwtProvider tokenProvider, UserDetailsServiceImpl userDetailsService) {
		this.tokenProvider = Objects.requireNonNull(tokenProvider, getNpeDescription("tokenProvider"));
		this.userDetailsService = Objects.requireNonNull(userDetailsService, getNpeDescription("userDetailsService"));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			String jwtToken = retrieveJwtToken(httpServletRequest);
			LOGGER.debug("jwtToken: " + jwtToken);

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

		} catch (Exception ex) {
			LOGGER.error("Cant set user authentication. Msg: {}" + ex.getMessage(), ex);
		}

		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	private String retrieveJwtToken(HttpServletRequest httpRequest) {
		String header = httpRequest.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			return header.replace("Bearer ", "");
		}
		return null;
	}
}
