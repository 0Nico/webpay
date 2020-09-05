package com.paymybuddy.webpay.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.webpay.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping(path = "/go", produces=MediaType.APPLICATION_JSON_VALUE)
	public void goRequest () {
		System.out.println("LOOOOOOOOOOOOOOL");
	}

}
