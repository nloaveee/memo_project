package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// 화면용 Controller

@Controller
@RequestMapping("/user")
public class UserController {

	
	/**
	 * 회원가입 화면 
	 * @return
	 */
	//http://localhost/user/sign-up-view
	@GetMapping("/sign-up-view")
	public String signUpView() {
		// 가운데 레이아웃 조각만 경로로 잡아주면 전체 레이아웃으로 구성된다.
		return "user/signUp";
	}
	
	
	/**
	 * 로그인 화면 
	 * @return
	 */
	//http://localhost/user/sign-in-view
	@GetMapping("/sign-in-view")
	public String signInView() {
		return "user/signIn";
	}
}
