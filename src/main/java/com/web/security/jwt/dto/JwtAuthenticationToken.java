package com.web.security.jwt.dto;

import com.web.security.member.type.MemberRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public JwtAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static JwtAuthenticationToken beforeOf(String accessToken) {
        return new JwtAuthenticationToken(accessToken, "");
    }

    public static Authentication afterOf(long memberId, MemberRole role) {
        return new JwtAuthenticationToken(memberId, "", List.of(role));
    }

    public String getAccessToken() {
        return (String) this.getPrincipal();
    }
}
