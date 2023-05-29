package com.web.security.jwt;

import com.web.security.jwt.dto.JwtAuthenticationToken;
import com.web.security.jwt.exception.InvalidAccessTokenException;
import com.web.security.member.type.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtHelper jwtHelper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        JwtAuthenticationToken before = (JwtAuthenticationToken) authentication;

        String accessToken = before.getAccessToken();
        if (!jwtHelper.validate(accessToken)) {
            throw new InvalidAccessTokenException();
        }

        long id = Long.parseLong(jwtHelper.extractSubject(accessToken));
        MemberRole role = MemberRole.valueOf(jwtHelper.extractRole(accessToken));
        return JwtAuthenticationToken.afterOf(id, role);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

