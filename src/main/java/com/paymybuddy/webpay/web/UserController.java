package com.paymybuddy.webpay.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.webpay.model.User;
import com.paymybuddy.webpay.model.dto.UserDto;
import com.paymybuddy.webpay.service.CustomUserDetailsService;
import com.paymybuddy.webpay.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	

	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDto getUserProfile () {
		
		return userService.getUser(userDetailsService.currentUser().getId());
	}
	
	@PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public User updateUserProfile (@RequestBody User user) {
		
		return userService.updateUser(user);
	}
	
	
	@DeleteMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public void deleteUserProfile () {
		
		userService.deleteUser(userDetailsService.currentUser().getId());
	}
	
	@PostMapping(path = "/create", produces=MediaType.APPLICATION_JSON_VALUE)
	public User createUserProfile (@RequestBody User user) {
		
		return userService.createUser(user);
	}
	
	
	@PostMapping(path = "/addFriend", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<String> addFriend (@RequestParam("email") String email) {
		
		userService.addContact(userDetailsService.currentUser().getId(), email);
		return getAllFriends();
	}
	
	
	@GetMapping(path = "/friends", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAllFriends () {
		
		List<String> friends = userService.getAllFriends(userDetailsService.currentUser());
		return friends;
	}
	
	
	@GetMapping(path = "/friend", produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDto getFriendDetails (@RequestParam("email") String email) {
		
		return userService.getFriendDetails(userDetailsService.currentUser(), email);
	}
	
	
	@GetMapping(path = "/list", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<String> getAppUsersList () {
		
		return userService.getAllEmailUser();
	}
	
	
	
	


}
