package com.hilltoponline.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CustomUserDetailInterceptor extends HandlerInterceptorAdapter {
	private final static Logger LOG = LoggerFactory.getLogger(CustomUserDetailInterceptor.class);

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView mav)
			throws Exception {
		
		//get the current logged in session if it exists
		Principal principal = request.getUserPrincipal();
		
		if (principal != null && mav != null) {
			//get a user object from the session and add it to the model template
			CustomUserDetail user = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			mav.getModelMap().addAttribute("user", user);
		}
		
	}


}
