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

// 응답이 JSON으로 내려오는 API를 사용하는 controller

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
	 * 회원가입
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
}
