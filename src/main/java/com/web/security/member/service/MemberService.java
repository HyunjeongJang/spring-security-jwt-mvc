package com.web.security.member.service;

import com.web.security.member.domain.Member;
import com.web.security.member.dto.MemberRegisterRequest;
import com.web.security.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    public void register(MemberRegisterRequest request) {
        if (memberMapper.existsByMemberId(request.getMemberId())) {
            throw new RuntimeException("중복된 이메일입니다!");
        }
        Member member = Member.of(request);
        memberMapper.register(member);
    }

}
