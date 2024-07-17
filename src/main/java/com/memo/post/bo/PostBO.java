package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;
import com.mysql.cj.Session;

@Service
public class PostBO {
	
	@Autowired
	private PostMapper postMapper;
	
	// input: 로그인 된 사람의 userId
	// output: List<Post> 
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	// input: 3개 파라미터 
	// ouput: x
	public void addPost(Session userId, String subject, String content, MultipartFile file) {
		postMapper.insertPost(userId, subject, content, file);
	}
}
