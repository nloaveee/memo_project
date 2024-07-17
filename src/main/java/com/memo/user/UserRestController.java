package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;


// 응답이 JSON으로 내려오는 API를 사용하는 controller
// user => JPA

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	private UserBO userBO;

	/**
	 * 아이디 중복확인 API
 	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id")
	public Map<String,Object> isDuplicatedId(@RequestParam("loginId") String loginId) {
		
		// DB 조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
				
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		
		if(user != null) { // 중복인 경우 
			result.put("is_duplicated_id", true);
		} else { // 중복이 아닌 경우
			result.put("is_duplicated_id", false);
		}
		return result;		
	}
	
	
	/**
	 * 회원가입 API
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId, 
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// password md5 알고리즘 => hashing
		// 항상 똑같은 값으로 hashing 된다.
		// ex) aaaa => 74b8733745420d4d33f80c4663dc5e5
		String hashedPassword = EncryptUtils.md5(password);
		
		// DB insert
		// 원본 password를 보내면 안되고, hashing된 password를 보내야 된다.
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if(user !=null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {        
			result.put("code", 500);
			result.put("error_message", "회원가입에 실패했습니다.");
		}
		
		return result;		
	}

	
	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		
		// password hasing
		// hasing 하기 위해서 원본 password를 넣어주면 hassing 된 password가 나온다. 
		String hashedPassword = EncryptUtils.md5(password); 
		
		// DB select 조회 => loginId, 해싱된 password를 가지고 조회 => UserEntity (채워져 있거나 null 이거나)
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		// 로그인 처리 및 응답값
		Map<String, Object> result =new HashMap<>();
		if(user != null) { // 로그인 성공
			// session에 사용자 정보를 담는다.(사용자 각각 마다)
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			result.put("code", 200);
			result.put("result", "성공");
		} else { // 로그인 실패 
			result.put("code", 403);
			result.put("error_message", "존재하지 않는 사용자 입니다.");
		}
		
		return result;
	}
	
}
