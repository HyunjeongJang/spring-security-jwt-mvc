package com.web.security.jwt;

import com.web.security.jwt.dto.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.web.security.security.exception.NotFoundAccessTokenException;

import java.io.IOException;
import java.util.Arrays;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	public JwtAuthenticationFilter(RequestMatcher matcher) {
		super(matcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException, IOException, ServletException {
		Object NotFoundAccessTokenException;
		String accessToken = Arrays.stream(req.getCookies())
			.filter(cookie -> cookie.getName().equals("access_token"))
			.findFirst()
			.orElseThrow(NotFoundAccessTokenException::new)
			.getValue();

		JwtAuthenticationToken before = JwtAuthenticationToken.beforeOf(accessToken);
		return super.getAuthenticationManager().authenticate(before);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		JwtAuthenticationToken afterOf = (JwtAuthenticationToken) authResult;

		SecurityContextHolder.clearContext();
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(afterOf);
		SecurityContextHolder.setContext(context);

		chain.doFilter(request, response);
	}


}
