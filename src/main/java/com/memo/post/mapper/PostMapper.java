package com.memo.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.post.domain.Post;

@Mapper
public interface PostMapper {
	
	public List<Map<String, Object>> selectPostListTest();
	
	// 글 목록 
	public List<Post> selectPostListByUserId(int userId);
	
	// 글 쓰기
	// output => null 이거나 행의 갯수 
	public void insertPost(
			@Param("userId") int userId,
			@Param("subject") String subject,
			@Param("content") String content,
			// MultipartFile는 DB에 저장 불가능 => BO에서 수정
			@Param("imagePath") String imagePath);
}
