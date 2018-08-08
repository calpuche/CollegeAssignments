package com.hilltoponline.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hilltoponline.repository.SectionRepository;
import com.hilltoponline.repository.TermRepository;
import com.hilltoponline.repository.UserRepository;
import com.hilltoponline.security.CustomUserDetail;

@Controller
@RequestMapping("/")
public class IndexController {
	private final static Logger LOG = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	SectionRepository sectionRepo;

	@Autowired
	TermRepository termRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getIndexPage(Model model, @AuthenticationPrincipal CustomUserDetail user) {
		//show dashboard for student and instructor only
		//LOG.debug("{} {} {} {}", new Object[]{user.getUsername(), user.getUserId(), user.getRoleId(), userRepo.getRoleById(user.getRoleId()).getRole()});
		if (userRepo.getRoleById(user.getRoleId()).getRole().equals("ROLE_STUDENT") || 
				userRepo.getRoleById(user.getRoleId()).getRole().equals("ROLE_INSTRUCTOR")) {
			model.addAttribute("schedule",sectionRepo.getSchedule(termRepo.getActiveTerm(), user));
			return new ModelAndView("index");
		}
		//show the manage users page for registrar
		else {
			return new ModelAndView("redirect:/user/manageUsers");
		}
		
	}
}
