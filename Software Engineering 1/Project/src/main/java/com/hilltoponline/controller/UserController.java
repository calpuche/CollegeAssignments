package com.hilltoponline.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hilltoponline.model.Role;
import com.hilltoponline.model.User;
import com.hilltoponline.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final static Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)	
	public ModelAndView loginPageGet(Model model,
                        @RequestParam(name = "error", required=false, defaultValue="false") boolean error,
                        @RequestParam(name = "logout", required=false, defaultValue="false") boolean logout) {
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/claimAccount", method = RequestMethod.GET)	
	public ModelAndView claimAccountGet(Model model) {
	   return new ModelAndView("claimAccount", "newUser", new User());
	}
   
	@RequestMapping(value = "/claimAccount", method = RequestMethod.POST)	
	public ModelAndView claimAccountPost(@RequestParam String passwordCheck,
			@Valid @ModelAttribute("user") User newUser, BindingResult result,  ModelMap model) {
		//return an error page if the input data was invalid
		if (result.hasErrors() || (!passwordCheck.equals(newUser.getPassword()))) {
			LOG.debug("Binding erros: {}\nPasswords equal?{}", new Object[]{result.toString(),passwordCheck.equals(newUser.getPassword())});
			model.addAttribute("error",true);
			return new ModelAndView("claimAccount", "newUser", newUser);
	    }
		else {
			//check to see if the claimable
			User claimableUser = userRepo.isUserClaimable(newUser);
			//insert user and password into database
			if (claimableUser != null && userRepo.updateUserPassword(claimableUser, newUser.getPassword())) {
				LOG.debug("Updating Password for: {}", claimableUser);
				model.addAttribute("username", claimableUser.getUsername());
				return new ModelAndView("info");
			} else {
				return new ModelAndView("redirect:/user/manageUsers?error=true");
			}
		}	
	}
	
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/manageUsers", method=RequestMethod.GET)
	public ModelAndView getAllUsersGet(ModelMap model,
			@RequestParam(name = "error", required=false, defaultValue="false") boolean error,
            @RequestParam(name = "created", required=false, defaultValue="false") boolean created,
            @RequestParam(name = "edited", required=false, defaultValue="false") boolean edited,
            @RequestParam(name = "removed", required=false, defaultValue="false") boolean removed) {
		
		Map<User, Role> userToRoleMap = new HashMap<>();
		userRepo.getAllUsers().stream().forEach(user -> userToRoleMap.put(user, userRepo.getRoleById(user.getRoleId())));
		model.addAttribute("allUsers", userToRoleMap);
		model.addAttribute("allRoles", userRepo.getAllRoles());
		model.addAttribute("error", error);
		model.addAttribute("created", created);
		model.addAttribute("edited", edited);
		model.addAttribute("removed", removed);
		return new ModelAndView("manageUsers", "newUser", new User());
	}
	
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public ModelAndView addUserPost(@Valid @ModelAttribute("user") User newUser, 
			BindingResult result,  ModelMap model) {
	
		//return an error page if the input data was invalid
		if (!result.hasErrors() && userRepo.addNewUser(newUser)) {
			LOG.debug("Adding new user: {}", newUser);
			return new ModelAndView("redirect:/user/manageUsers?created=true");
	    }
		else {
			String errors = result.getAllErrors().toString();
			LOG.warn("Error adding new user: {}", errors.length() > 2 ? errors : "SQL Exception");
			model.addAttribute("error",true);
			model.addAttribute("allUsers", userRepo.getAllUsers());
			model.addAttribute("allRoles", userRepo.getAllRoles());
			return new ModelAndView("manageUsers", "newUser", newUser);
		}	
	}
	
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/editUser/{userId}", method=RequestMethod.GET)
	public ModelAndView editUserGet(@PathVariable Integer userId, ModelMap model,
			@RequestParam(name = "error", required=false, defaultValue="false") boolean error) {
		model.addAttribute("allRoles", userRepo.getAllRoles());
		model.addAttribute("error",error);
		model.addAttribute("userToEdit", userRepo.getUserById(userId));
		return new ModelAndView("editUser");
	}
	
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/editUser", method=RequestMethod.POST)
	public ModelAndView editUserPost(@Valid @ModelAttribute("user") User userToEdit, 
			BindingResult result,  ModelMap model) {
		//return an error page if the input data was invalid
		if (!result.hasErrors() && userRepo.updateUser(userToEdit)) {
			LOG.debug("Updating user: {}", userToEdit);
			return new ModelAndView("redirect:/user/manageUsers?edited=true");
	    }
		else {
			String errors = result.getAllErrors().toString();
			LOG.warn("Error adding new user: {}", errors.length() > 2 ? errors : "SQL Exception");
			return new ModelAndView(String.format("redirect:/user/manageUsers/%d/?error=true", userToEdit.getUserId()));
		}	
	}
	
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/removeUser/{userId}", method=RequestMethod.GET)
	public ModelAndView deleteUserPost (@PathVariable Integer userId, ModelMap model) {
		LOG.debug("REMOVING USER: {}",userRepo.getUserById(userId));
		userRepo.removeUserById(userId);
		return new ModelAndView("redirect:/user/manageUsers?removed=true");
	}
}
