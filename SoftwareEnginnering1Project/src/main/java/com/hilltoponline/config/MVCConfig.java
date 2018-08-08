package com.hilltoponline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hilltoponline.security.CustomUserDetailInterceptor;

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new CustomUserDetailInterceptor()).addPathPatterns("/**");
	}

}
