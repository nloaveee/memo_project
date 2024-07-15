package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

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
	
	
	/**
	 * 로그아웃 API
	 * @param session
	 * @return
	 */
	@RequestMapping("/sign-out")
	public String signOut(HttpSession session) {
		// session 내용 비움
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		// 로그인 페이지로 이동 
		return "redirect:/user/sign-in-view";
	}
}
