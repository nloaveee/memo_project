package com.memo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	//JPQL
	// 중복확인
	public UserEntity findByLoginId(String loginId);
	
	// 로그인
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
	
}
