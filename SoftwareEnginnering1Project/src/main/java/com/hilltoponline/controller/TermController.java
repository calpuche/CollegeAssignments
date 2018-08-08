package com.hilltoponline.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hilltoponline.model.Term;
import com.hilltoponline.repository.TermRepository;

@Controller
@RequestMapping("/term")
public class TermController {
	
	private final static Logger LOG = LoggerFactory.getLogger(TermController.class);

	@Autowired
	TermRepository termRepo;
	
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public ModelAndView getTermPage(Model model,
			@RequestParam(name= "editedTerm",required=true,defaultValue="false")boolean editedTerm,
			@RequestParam(name="addedTerm",required=true,defaultValue="false")boolean addedTerm){
		List<Term> allTerms = termRepo.getAllTerms();
		model.addAttribute("allTerms",allTerms);
		model.addAttribute("editedTerm",editedTerm);
		model.addAttribute("addedTerm", addedTerm);
		return new ModelAndView("term");
	}	
	
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/editTerm",method = RequestMethod.POST)
	public ModelAndView editTermPage(Model model,@Valid @ModelAttribute Term editedTerm){
		 //System.out.println(editedTerm.toString());
		 termRepo.editTerm(editedTerm);
		
		
		return new ModelAndView("redirect:/term/?editedTerm=true");
	}
	
	@PreAuthorize("hasRole('REGISTRAR')")
	@RequestMapping(value="/addTerm",method = RequestMethod.POST)
	public ModelAndView addTermPage(Model model,@Valid @ModelAttribute Term newTerm, BindingResult result){
		if(newTerm.getTermOpenForRegistration() == null){
			newTerm.setTermOpenForRegistration(false);
			
		}
		if(newTerm.getTermActive() == null){
			newTerm.setTermActive(false);
		}
		LOG.debug("{} {}", newTerm.toString(), result.toString());
		 //System.out.println(newTerm.toString());
		 termRepo.addTerm(newTerm);
		
		
		return new ModelAndView("redirect:/term/?addedTerm=true");
	}
}
