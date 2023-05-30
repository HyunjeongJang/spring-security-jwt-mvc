package com.web.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping({"/",""})
	public String homeController() {
		return "index";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "memberSignUp";
	}

	@GetMapping("/loginPage")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/admin")
	public String admin() {
		return "adminPage";
	}
	
	@GetMapping("/user")
	public String board() {
		return "userPage";
	}

}
