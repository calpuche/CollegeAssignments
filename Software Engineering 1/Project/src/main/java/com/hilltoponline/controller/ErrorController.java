package com.hilltoponline.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {
	
	@Autowired
    private ErrorAttributes errorAttributes;
	
	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView getIndexPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(requestAttributes, true);
		
		model.addAttribute("errorTitle", errorAttributesMap.get("status"));
		model.addAttribute("errorText", errorAttributesMap.get("error"));
		model.addAttribute("errorMessage", errorAttributesMap.get("message"));
		model.addAttribute("errorTime", errorAttributesMap.get("timestamp").toString());
		
		return new ModelAndView("error");
	}
}
