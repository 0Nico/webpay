package com.paymybuddy.webpay.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.paymybuddy.webpay.WebpayApplication;
import com.paymybuddy.webpay.config.AbstractTest;
import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.User;
import com.paymybuddy.webpay.model.dto.UserDto;
import com.paymybuddy.webpay.service.UserService;

public class UserServiceTest extends AbstractTest{
	
	@Autowired
	UserService userService;
	
	@Autowired
	IUserRepository userRepository;
	
	
	@Test
	public void getAllUsersTest() {
		
		List<String> usersList = userService.getAllEmailUser();
		
		assertTrue(usersList.size() > 0);
		assertTrue(usersList.contains("denis.wilson@mail.com"));
	}
	
	@Test
	public void getUserTest() {
		
		UserDto userDto = userService.getUser("b9a85f0e-8e27-4203-a717-e241ed4c8fb9");
		
		assertTrue(userDto != null);
		assertEquals(userDto.getEmail(), "denis.wilson@mail.com");
	}
	
	@Test
	public void createUserTest() {
		
		User user = new User();
		user.setEmail("jamie.lannister@mail.com");
		user.setFirstName("Jamie");
		user.setLastName("Lannister");
		user.setPassword("MotdePass");
		user.setRole("USER");
		
		userService.createUser(user);
		
		User userSaved = userRepository.findByEmail("jamie.lannister@mail.com");
		
		assertTrue(userSaved != null);
		assertEquals(userSaved.getLastName(), "Lannister");
		assertEquals(userSaved.getId(), user.getId());
	}
	
	@Test
	public void updateUserTest() {
		
		User user = userRepository.findByEmail("denis.wilson@mail.com");
		user.setFirstName("Louis");
		userService.updateUser(user);
		
		User userUpdated = userRepository.findByEmail("denis.wilson@mail.com");
		assertTrue(userUpdated != null);
		assertEquals(userUpdated.getFirstName(), "Louis");
		
	}
	
	@Test
	public void getFriendDetailsTest() {
		
		User user = userRepository.findByEmail("denis.wilson@mail.com");
		UserDto friendDto = userService.getFriendDetails(user, "clark.gaby@mail.com");
		
		assertTrue(friendDto != null);
		assertEquals(friendDto.getFirstName(), "Gaby");
	}
	
	@Test
	public void deleteUserTest() {
		
		
		userService.deleteUser(userRepository.findByEmail("janet.jackson@mail.com").getId());
		
		User userDeleted = userRepository.findByEmail("janet.jackson@mail.com");
		assertTrue(userDeleted == null);
		
	}

	@Test
	public void getAllFriendsTest() {
		
		User user = userRepository.findByEmail("denis.wilson@mail.com");
		List<String> contactsList = userService.getAllFriends(user);
		
		assertTrue(contactsList.size() > 0);
		assertTrue(contactsList.contains("clark.gaby@mail.com"));
	}
	
	@Test
	public void addContactTest() {
		
		User user = userRepository.findByEmail("denis.wilson@mail.com");
		userService.addContact(user.getId(), "louise.holy@mail.com");
		
		User updatedUser =  userRepository.findByEmail("denis.wilson@mail.com");
		assertTrue(updatedUser.getContacts().size() > 0);
		
		List<String> contactsList = userService.getAllFriends(updatedUser);
		assertTrue(contactsList.contains("louise.holy@mail.com"));
	}
}
