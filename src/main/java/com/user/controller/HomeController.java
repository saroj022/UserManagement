package com.user.controller;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.user.model.UserDtls;
import com.user.repository.UserRepository;
import com.user.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	
	@PostMapping("/createUser")
	public String createuser(@ModelAttribute UserDtls user, HttpSession session) {

		// System.out.println(user);

		boolean f = userService.checkEmail(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email Id alreday exists");
		}

		else {
			UserDtls userDtls = userService.createUser(user);
			if (userDtls != null) {
				session.setAttribute("msg", "Register Sucessfully");
			} else {
				session.setAttribute("msg", "Something wrong on server");
			}
		}

		return "redirect:/";
	}
	
	///you have to update the user details
	
	
	
	@PutMapping("/updateUser")
	public String updateUser(@ModelAttribute UserDtls user, HttpSession session) {

		// System.out.println(user);

		boolean f = userService.checkEmail(user.getEmail());

		if (f) {
			session.setAttribute("msg", "Email Id doesn't exists");
		}

		else {
			
			UserDtls dt=userRepo.getById(user.getId());
			
			dt.setAddress(user.getAddress());
			dt.setEmail(user.getEmail());
			dt.setPassword(user.getPassword());
			dt.setFullName(user.getFullName());
			
			session.setAttribute("msg", "Profile updated successfully.");
		}

		return "redirect:/updated";
	}
	
	

}