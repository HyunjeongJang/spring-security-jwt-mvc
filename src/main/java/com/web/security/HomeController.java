package com.web.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	// 홈
	@GetMapping({"/",""})
	public String homeController() {
		return "index";
	}

	// 로그인 페이지
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/admin/memberList")
	public String admin() {
		return "member/memberListView";
	}
	
	@GetMapping("/board")
	public String board() {
		return "board/board";
	}

	// 회원가입 페이지
	@GetMapping("/joinForm")
	public String joinForm() {
		return "member/memberSignUp";
	}

}
