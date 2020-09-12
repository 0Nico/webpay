package com.paymybuddy.webpay.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.paymybuddy.webpay.WebpayApplication;
import com.paymybuddy.webpay.config.AbstractTest;
import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.Transaction;
import com.paymybuddy.webpay.model.User;
import com.paymybuddy.webpay.model.dto.TransactionDto;
import com.paymybuddy.webpay.model.dto.UserDto;
import com.paymybuddy.webpay.service.TransactionService;

public class TransactionServiceTest extends AbstractTest{

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	IUserRepository userRepository;
	
	
	
	@Test
	public void getTransactionTest() {
		
		TransactionDto transactionDto = transactionService.getTransaction(
				"725e6436-b38b-4062-a191-f7d4dde4131a", "b9a85f0e-8e27-4203-a717-e241ed4c8fb9");
		
		assertTrue(transactionDto != null);
		assertEquals(transactionDto.getBeneficiaryEmail(), "clark.gaby@mail.com");
	}

	@Test
	public void getTransactionsListTest() {
		
		List<TransactionDto> transList = transactionService.getTransactionsByUser("b9a85f0e-8e27-4203-a717-e241ed4c8fb9");
		
		assertTrue(transList.size() > 0);
		assertEquals(transList.get(0).getBeneficiaryEmail(), "clark.gaby@mail.com");
		
	}
	
	
	@Test
	public void sendMoneyTest() {
		
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setBeneficiaryEmail("clark.gaby@mail.com");
		transactionDto.setCashAmount(50.0);
		transactionDto.setCurrency("Dollar");
		transactionDto.setDescription("yes");
		transactionDto.setSenderEmail("denis.wilson@mail.com");
		User user = userRepository.findOne("b9a85f0e-8e27-4203-a717-e241ed4c8fb9");
		
		
		UserDto userDto = transactionService.sendMoney(user.getId(), transactionDto);
		
		assertTrue(userDto != null);
		assertEquals(userDto.getCashAmount(), user.getCashAmount()-transactionDto.getCashAmount() );
	}

}
