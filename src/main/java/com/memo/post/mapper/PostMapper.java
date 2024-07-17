package com.memo.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.domain.Post;
import com.mysql.cj.Session;

@Mapper
public interface PostMapper {
	
	public List<Map<String, Object>> selectPostListTest();
	
	// 글 목록 
	public List<Post> selectPostListByUserId(int userId);
	
	
	// output => null 이거나 행의 갯수 
	public void insertPost(
			@Param("userId") Session userId,
			@Param("subject") String subject,
			@Param("content") String content,
			@Param("file") MultipartFile file);
}
