package com.web.security.member.mapper;

import com.web.security.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void register(Member member);

    boolean existsByMemberId(String memberId);

	Member findByMemberId(String memberId);

}
