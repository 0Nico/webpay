package com.paymybuddy.webpay.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import com.paymybuddy.webpay.config.AbstractIntegrationTest;
import com.paymybuddy.webpay.model.dto.BankAccountDto;
import com.paymybuddy.webpay.model.dto.TransactionDto;
import com.paymybuddy.webpay.model.dto.UserDto;

public class BankAccountControllerTest extends AbstractIntegrationTest{
	
	final String uri = "/bankAccount";
	
	
	@Test
	public void getBankAccountTest() throws Exception {
		mvc.perform(get(uri).param("id", "a952e49c-54bf-4e7d-84f4-4458c7432442")
			      ).andDo(print()).andExpect(status().isOk())
			      .andExpect(content().contentType("application/json"))
			    .andExpect(jsonPath("$.ownerFirstName").value("Denis"))
			    .andExpect(jsonPath("$.ownerLastName").value("Wilson"))
			    .andExpect(jsonPath("$.bankName").value("SuperBank"));
		
	}
	
	
	@Test
	public void createBankAccountTest() throws Exception {
		
		BankAccountDto bankAccountDto = new BankAccountDto();
		bankAccountDto.setOwnerLastName("Wilson");
		bankAccountDto.setIban("JKJD-972GQCX-APEBS72-GGGTE");
		bankAccountDto.setBankName("Goldman");
		bankAccountDto.setOwnerFirstName("Denis");
		mvc.perform(post(uri).content(super.mapToJson(bankAccountDto))
	    		.contentType("application/json")).andDo(print()).andExpect(status().isOk())
	      
	      .andExpect(content().contentType("application/json"))
	      .andExpect(jsonPath("$.iban").value("JKJD-972GQCX-APEBS72-GGGTE"))
		.andExpect(jsonPath("$.bankName").value("Goldman"))
		.andExpect(jsonPath("$.owner.firstName").value("Denis"));
	      
	}

	
	@Test
	public void deleteBankAccountTest() throws Exception {
		
		mvc.perform(delete(uri).param("id", "3333449c-54bf-4e7d-84f4-445898362442")
			      ).andDo(print()).andExpect(status().isOk());
	
		mvc.perform(get(uri).param("id", "3333449c-54bf-4e7d-84f4-445898362442")
			      ).andDo(print()).andExpect(status().isNotFound());
	}
	
	
	@Test
	public void addMoneyFromBankAccountTest() throws Exception {
		
		MvcResult mvcResult = mvc.perform(get("/user")
			      ).andDo(print()).andExpect(status().isOk()).andReturn();
			   
		double userCashBeforeTransaction = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UserDto.class).getCashAmount();
		
		
		mvc.perform(post(uri + "/addMoneyFromBank")
				.param("cashAmount", "7500.0")
				.param("accountId", "a952e49c-54bf-4e7d-84f4-4458c7432442")
	    		).andDo(print()).andExpect(status().isOk())
		
		.andExpect(jsonPath("$.cashAmount").value(userCashBeforeTransaction + 7500.0))
	    .andExpect(jsonPath("$.description", Matchers.containsString("SuperBank")));
		
	     
	}
	
	
	@Test
	public void sendMoneyFromBankAccountTest() throws Exception {
		
		MvcResult mvcResult = mvc.perform(get("/user")
			      ).andDo(print()).andExpect(status().isOk()).andReturn();
			   
		double userCashBeforeTransaction = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UserDto.class).getCashAmount();
		
		
		mvc.perform(post(uri + "/sendMoneyToBank")
				.param("cashAmount", "300.0")
				.param("accountId", "a952e49c-54bf-4e7d-84f4-4458c7432442")
	    		).andDo(print()).andExpect(status().isOk())
		
		.andExpect(jsonPath("$.cashAmount").value(userCashBeforeTransaction  - 300.0))
	    .andExpect(jsonPath("$.description", Matchers.containsString("SuperBank")));
		
	     
	}
	
	
}
