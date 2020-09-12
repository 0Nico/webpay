package com.paymybuddy.webpay.service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.User;
import com.paymybuddy.webpay.model.dto.UserDto;

@Service
public class UserService {

	@Autowired
	IUserRepository userRepository;

	
	@Autowired
	BankAccountService bankService;
	
	
	public User createUser(User user) {
		user.setCashAmount(0.0);
		userRepository.create(user);
		return userRepository.findOne(user.getId());
    }
 
    public List<String> getAllEmailUser() {
        return userRepository.findAll().stream().map(User::getEmail).collect(Collectors.toList());
    }
 
    public UserDto getUser(String id) {
    	
    	User user = userRepository.findOne(id);
    	UserDto userDto = new UserDto();
   		userDto.setEmail(user.getEmail());
   		userDto.setFirstName(user.getFirstName());
   		userDto.setLastName(user.getLastName());
   		userDto.setCashAmount(user.getCashAmount());
        return userDto;
    }
    
    
    public UserDto getFriendDetails(User user, String email) {
    	
    	User friend = user.getContacts().stream().filter(usr -> usr.getEmail().equals(email)).findAny()
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Friend Does Not Exist"));
    	
   		UserDto userDto = new UserDto();
   		userDto.setEmail(email);
   		userDto.setFirstName(friend.getFirstName());
   		userDto.setLastName(friend.getLastName());
        return userDto;
    }
    
    public User updateUser(User user) {
    	if (user != null) {
    		return userRepository.update(user);
    	} else {
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User could Not Be Null");
    	}
    }

    public void deleteUser(String userId) {
    	
    	userRepository.deleteById(userId);
    }
    
    public void addContact(String userId, String email){
    	User user = userRepository.findOne(userId);
    	User contact = userRepository.findByEmail(email);
    	if(contact!= null) {
    		user.getContacts().add(contact);
    		updateUser(user);
    	} else {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Not Found");
    	}
    }
    
    
    
   
   
   public List<String> getAllFriends(User user){
	   
	   return user.getContacts().stream().map(User::getEmail).collect(Collectors.toList());
   }
    
}
