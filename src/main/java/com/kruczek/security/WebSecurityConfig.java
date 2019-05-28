package com.kruczek.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kruczek.auth.services.UserDetailsServiceImpl;
import com.kruczek.security.jwt.JwtAuthEntryPoint;
import com.kruczek.security.jwt.JwtAuthTokenFilter;
import com.kruczek.security.jwt.JwtProvider;

import static com.kruczek.utils.NpeChecker.getNpeDescription;

@Configuration // oznacza klasę jako definicję beana dla contextu aplikacji
@EnableWebSecurity
//wlacza security z poziomu wykonania metody --> w sensie, ze sprawdza czy dany uzytkownik moze wykonac metode
@EnableGlobalMethodSecurity(prePostEnabled = true/*pozwala na adnotacje @PreAuthorize nad metodami*/)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsServiceImpl userService;
	private final JwtAuthEntryPoint unauthorizedHandler;
	private final JwtProvider tokenProvider;
	private final UserDetailsServiceImpl userDetailsService;

	@Autowired
	public WebSecurityConfig(
			UserDetailsServiceImpl userService,
			JwtAuthEntryPoint unauthorizedHandler,
			JwtProvider tokenProvider,
			UserDetailsServiceImpl userDetailsService) {
		this.userService = Objects.requireNonNull(userService, getNpeDescription("userService"));
		this.unauthorizedHandler = Objects.requireNonNull(unauthorizedHandler, getNpeDescription("unauthorizedHandler"));
		this.tokenProvider = Objects.requireNonNull(tokenProvider, "tokenProvider");
		this.userDetailsService = Objects.requireNonNull(userDetailsService, "userDetailsService");
	}

	@Bean
	public JwtAuthTokenFilter jwtAuthTokenFilter() {
		return new JwtAuthTokenFilter(tokenProvider, userDetailsService);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return super.userDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationBuilder) throws Exception {
		authenticationBuilder
				.userDetailsService(userService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/auth/**", "/h2-console").permitAll()
				.antMatchers("/api/tasks").access("hasRole('ROLE_USER')")
				.antMatchers("/api/months/**", "/api/days/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
