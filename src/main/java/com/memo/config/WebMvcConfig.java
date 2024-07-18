package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration // 설정을 위한 Spring bean
public class WebMvcConfig implements WebMvcConfigurer {

	// 이미지 path와 서버에 업로드 된 실제 이미지와 매핑 설정 
	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
		.addResourceHandler("/images/**") // web path => http://localhost/images/aaaa_1721209539165/garden-4647544_1280.jpg
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // 실제 이미지 파일 위치 (window는 처음 /// 3개)
	}
}
