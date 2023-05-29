package com.web.security.security.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;
import java.util.stream.Collectors;

public class FilterSkipMatcher implements RequestMatcher {

	private final OrRequestMatcher matcher;

	public FilterSkipMatcher(List<String> skipPaths) {
		this.matcher = new OrRequestMatcher(skipPaths.stream()
			.map(AntPathRequestMatcher::new)
			.collect(Collectors.toList()));
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		return !matcher.matches(request);
	}

}
