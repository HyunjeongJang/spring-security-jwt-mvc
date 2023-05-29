package com.web.security.security.exception;

import org.springframework.security.core.AuthenticationException;

public class NotFoundAccessTokenException extends AuthenticationException {

	public NotFoundAccessTokenException() {
		super("AccessToken 을 찾을 수 없습니다.");
	}

}
