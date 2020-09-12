package com.paymybuddy.webpay.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.webpay.config.AbstractIntegrationTest;
import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.User;

public class UserControllerTest extends AbstractIntegrationTest {

	final String uri = "/user";
	
	@Autowired
	IUserRepository userRepository;
	
	@Test
	public void getUserProfileTest() throws Exception {
	    mvc.perform(get(uri)
	      ).andDo(print()).andExpect(status().isOk())
	      .andExpect(content().contentType("application/json"))
	      .andExpect(jsonPath("$.firstName").value("Denis"))
	    .andExpect(jsonPath("$.lastName").value("Wilson"))
	    .andExpect(jsonPath("$.cashAmount").value(500.0));
	}
	
	
	@Test
	public void updateUserTest() throws Exception {
		User user = userRepository.findByEmail("denis.wilson@mail.com");
		user.setLastName("Washington");
	    mvc.perform(put(uri).content(super.mapToJson(user))
	    		.contentType("application/json")).andDo(print()).andExpect(status().isOk())
	      
	      .andExpect(content().contentType("application/json"))
	      .andExpect(jsonPath("$.firstName").value("Denis"))
	      .andExpect(jsonPath("$.email").value("denis.wilson@mail.com"))
	    .andExpect(jsonPath("$.lastName").value("Washington"));
	}

	
	@Test
	@WithAnonymousUser
	public void createUserTest() throws Exception {
		User user = new User();
		user.setFirstName("Abraham");
		user.setEmail("abraham.lincoln@mail.com");
		user.setPassword("UnitedStates");
		user.setLastName("Lincoln");
		user.setRole("USER");
	    mvc.perform(post(uri + "/create").content(super.mapToJson(user))
	    		.contentType("application/json")).andDo(print()).andExpect(status().isOk())
	      
	      .andExpect(content().contentType("application/json"))
	      .andExpect(jsonPath("$.firstName").value("Abraham"))
	      .andExpect(jsonPath("$.email").value("abraham.lincoln@mail.com"))
	    .andExpect(jsonPath("$.lastName").value("Lincoln"));
	}

	
	
	
	@Test
	public void addFriendTest() throws Exception {
	    mvc.perform(post(uri + "/addFriend").param("email", "louise.holy@mail.com")
	      ).andDo(print()).andExpect(status().isOk())
	      
	      .andExpect(content().contentType("application/json"))
	      .andExpect(jsonPath("$.[*]", Matchers.hasItem("louise.holy@mail.com")));
	      
	}
	
	
	@Test
	public void getAllFriendsTest() throws Exception {
	    mvc.perform(get(uri + "/friends")
	      ).andDo(print()).andExpect(status().isOk())
	      .andExpect(content().contentType("application/json"))
	    .andExpect(jsonPath("$.[*]", Matchers.hasItem("clark.gaby@mail.com")))
	    .andExpect(jsonPath("$.[*]", Matchers.hasItem("luckyluc@mail.com")));
	}
	
	
	@Test
	public void getFriendDetailsTest() throws Exception {
	    mvc.perform(get(uri + "/friend").param("email", "clark.gaby@mail.com")
	      ).andDo(print()).andExpect(status().isOk())
	      .andExpect(content().contentType("application/json"))
	      .andExpect(jsonPath("$.firstName").value("Gaby"))
	      .andExpect(jsonPath("$.email").value("clark.gaby@mail.com"))
	    .andExpect(jsonPath("$.lastName").value("Clark"));
	}
	
	
	@Test
	public void getAppUserListTest() throws Exception {
	    mvc.perform(get(uri + "/list")
	      ).andDo(print()).andExpect(status().isOk())
	      .andExpect(content().contentType("application/json"))
	    .andExpect(jsonPath("$.[*]", Matchers.hasItem("clark.gaby@mail.com")))
	    .andExpect(jsonPath("$.[*]", Matchers.hasItem("denis.wilson@mail.com")))
	    .andExpect(jsonPath("$.[*]", Matchers.hasItem("louise.holy@mail.com")))
	    .andExpect(jsonPath("$.[*]", Matchers.hasItem("janet.jackson@mail.com")))
	    .andExpect(jsonPath("$.[*]", Matchers.hasItem("luckyluc@mail.com")));
	}
	
	
	@Test
	@WithMockUser(username = "janet.jackson@mail.com", password = "GoDeep", roles = "USER")
	public void deleteUserTest() throws Exception {
	    mvc.perform(delete(uri)
	      ).andDo(print()).andExpect(status().isOk());
	      
	      User user = userRepository.findByEmail("janet.jackson@mail.com");
	      assertTrue(user == null);
	      
	}
}
