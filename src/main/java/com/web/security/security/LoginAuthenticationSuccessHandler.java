package com.web.security.security;

import com.web.security.jwt.JwtHelper;
import com.web.security.member.type.MemberRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtHelper jwtHelper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.info("{} 사용자 로그인 성공!", authentication.getPrincipal());

		MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
		MemberRole role = (MemberRole) authentication.getAuthorities().stream().findFirst().get();

		String accessToken = jwtHelper.generateAccessToken(myUserDetails.getId(), String.valueOf(role));
		Cookie accessTokenCookie = new Cookie("access_token", accessToken);
		accessTokenCookie.setHttpOnly(true);
		long expiration = jwtHelper.extractExpiredAt(accessToken);
		int maxAge = (int) ((expiration - new Date(System.currentTimeMillis()).getTime()) / 1000);
		accessTokenCookie.setMaxAge(maxAge);

		response.addCookie(accessTokenCookie);
		if (role.equals(MemberRole.USER)) {
			response.sendRedirect("/user");
		} else if (role.equals(MemberRole.ADMIN)) {
			response.sendRedirect("/admin");
		}
	}

}
