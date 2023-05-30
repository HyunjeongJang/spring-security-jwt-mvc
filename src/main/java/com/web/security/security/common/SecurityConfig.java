package com.web.security.security.common;

import com.web.security.jwt.JwtAuthenticationFilter;
import com.web.security.jwt.JwtAuthenticationProvider;
import com.web.security.security.LoginAuthenticationFailureHandler;
import com.web.security.security.LoginAuthenticationSuccessHandler;
import com.web.security.security.exception.handler.CustomAccessDeniedHandler;
import com.web.security.security.exception.handler.CustomAuthenticationEntryPoint;
import com.web.security.security.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final MyUserDetailsService userDetailsService;
	private final LoginAuthenticationSuccessHandler loginSuccessHandler;
	private final LoginAuthenticationFailureHandler loginFailureHandler;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	private static final List<String> DEFAULT_JWT_WHITE_LIST_URL = List.of("/", "/joinForm", "/member/register", "/loginForm", "/login");

	public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		List<String> skipPaths = new ArrayList<>(DEFAULT_JWT_WHITE_LIST_URL);
		FilterSkipMatcher matcher = new FilterSkipMatcher(skipPaths);

		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(matcher);
		jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
		jwtAuthenticationFilter.setAuthenticationFailureHandler(new AuthenticationEntryPointFailureHandler(authenticationEntryPoint));
		return jwtAuthenticationFilter;
	}

	@Bean
	public AuthenticationManager configureAuthenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(AuthenticationManager authenticationManager, HttpSecurity http) throws Exception {
		// FormLogin disable 안하면
		return http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()

			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/", "/loginForm", "/login", "/joinForm", "/member/register").permitAll()
				.requestMatchers("/user/**").hasAuthority("USER")
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginPage("/loginForm")
				.loginProcessingUrl("/login")
				.usernameParameter("memberId")
				.passwordParameter("password")
				.successHandler(loginSuccessHandler)
				.failureHandler(loginFailureHandler)
			)
			.logout(LogoutConfigurer::permitAll)

			.userDetailsService(userDetailsService)

			.addFilterBefore(jwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)

			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler)
			.and()

			.build();
	}

}
