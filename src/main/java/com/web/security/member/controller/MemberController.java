package com.web.security.member.controller;

import com.web.security.member.dto.MemberRegisterRequest;
import com.web.security.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String memberRegister(MemberRegisterRequest request, Model model) {
        request.encryptPassword(passwordEncoder);
        memberService.register(request);
        return "loginForm";
    }
}
