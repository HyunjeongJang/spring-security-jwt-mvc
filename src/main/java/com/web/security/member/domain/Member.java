package com.web.security.member.domain;

import com.web.security.member.dto.MemberRegisterRequest;
import com.web.security.member.type.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member {

    private long id;
    private String memberId;
    private String password;
    private String name;
    private MemberRole role;

    public static Member of(MemberRegisterRequest request) {
        return Member.builder()
                .memberId(request.getMemberId())
                .password(request.getPassword())
                .name(request.getName())
                .role(MemberRole.USER)
                .build();
    }
}
