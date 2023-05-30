package com.web.security.security;

import com.web.security.security.exception.NotFoundMemberAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		log.error("로그인 실패", exception);

		String message = "Invalid User ID or Password!";
		if (exception.getCause() instanceof NotFoundMemberAuthenticationException) {
			message = "Invalid User ID!";
		} else if (exception instanceof BadCredentialsException) {
			message = "Invalid User Password!";
		}

		setDefaultFailureUrl("/loginForm?error=true&message=" + message);
		super.onAuthenticationFailure(request, response, exception);
	}

}
