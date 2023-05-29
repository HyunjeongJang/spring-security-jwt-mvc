package com.web.security.security.service;

import com.web.security.member.domain.Member;
import com.web.security.member.mapper.MemberMapper;
import com.web.security.security.MyUserDetails;
import com.web.security.security.exception.NotFoundMemberAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberMapper.findByMemberId(memberId);
        if (member == null) {
            throw new NotFoundMemberAuthenticationException();
        }
        return new MyUserDetails(member);
    }

}
