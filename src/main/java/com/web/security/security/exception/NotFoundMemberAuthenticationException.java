package com.web.security.security.exception;

import org.springframework.security.core.AuthenticationException;

public class NotFoundMemberAuthenticationException extends AuthenticationException {

	public NotFoundMemberAuthenticationException() {
		super("존재하지 않는 사용자입니다.");
	}

}
