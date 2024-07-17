package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	@Autowired
	private PostBO postBO;

	// 글쓰기 후 저장 
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("content") String content, 
			// null 가능 => required = false
			@RequestParam(value ="file", required = false) MultipartFile file, 
			HttpSession session) {
		
		// session에서 글쓴이 번호 꺼내기
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// DB insert 요청
		postBO.addPost(userId, userLoginId, subject, content, file);
				
		//응답값 
		Map<String, Object> result =new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
		
	}
}
