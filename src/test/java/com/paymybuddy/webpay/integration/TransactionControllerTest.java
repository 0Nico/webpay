package com.paymybuddy.webpay.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashMap;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.paymybuddy.webpay.config.AbstractIntegrationTest;
import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.User;
import com.paymybuddy.webpay.model.dto.TransactionDto;

public class TransactionControllerTest extends AbstractIntegrationTest{

	final String uri = "/transaction";
	
	@Autowired
	IUserRepository userRepository;
	
	
	@Test
	public void getAllTransactionsByUserTest() throws Exception{
		
		MvcResult mvcResult = mvc.perform(get(uri + "/list")
			      ).andDo(print()).andExpect(status().isOk()).andReturn();
			   
		String content = mvcResult.getResponse().getContentAsString();
			   
		List<LinkedHashMap> transactionList = super.mapFromJson(content, List.class);
		assertTrue(transactionList.size() > 0);
		assertTrue(transactionList.stream().anyMatch(transa -> transa.get("senderEmail").equals("denis.wilson@mail.com")));
	}
	
	
	@Test
	public void getTransactionTest() throws Exception{
		mvc.perform(get(uri).param("id", "725e6436-b38b-4062-a191-f7d4dde4131a")
			      ).andDo(print()).andExpect(status().isOk())
			      .andExpect(content().contentType("application/json"))
			    .andExpect(jsonPath("$.senderEmail").value("denis.wilson@mail.com"))
			    .andExpect(jsonPath("$.currency").value("Dollar"))
			    .andExpect(jsonPath("$.beneficiaryEmail").value("clark.gaby@mail.com"));
	}
	
	
	@Test
	public void executeMoneyTransferTest() throws JsonProcessingException, Exception {
		
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setBeneficiaryEmail("clark.gaby@mail.com");
		transactionDto.setCashAmount(40.0);
		transactionDto.setCurrency("Dollar");
		transactionDto.setDescription("Bla Bla");
		transactionDto.setSenderEmail("denis.wilson@mail.com");
		double denisCashBeforeTransfer = userRepository.findByEmail("denis.wilson@mail.com").getCashAmount();
		
	    mvc.perform(post(uri).content(super.mapToJson(transactionDto))
	    		.contentType("application/json")).andDo(print()).andExpect(status().isOk())
	      
	      .andExpect(content().contentType("application/json"))
	      .andExpect(jsonPath("$.cashAmount").value(denisCashBeforeTransfer - transactionDto.getCashAmount()));
	      
	}
	
}
