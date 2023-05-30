package com.web.security.security.exception.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws
		IOException, ServletException {
		/**
		 *  403 (FORBIDDEN)
		 */
		log.error("No permissions", accessDeniedException);
		// TODO : 요 페이지로 이동하게
		res.sendRedirect(String.format("/myError?error=%s", accessDeniedException.getMessage()));
	}

}

