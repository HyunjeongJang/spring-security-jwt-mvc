package com.web.security.member.type;

import org.springframework.security.core.GrantedAuthority;

public enum MemberRole implements GrantedAuthority {

    USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
