package com.paymybuddy.webpay.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.paymybuddy.webpay.WebpayApplication;
import com.paymybuddy.webpay.dao.bankAccountDao.IBankAccountRepository;
import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.BankAccount;
import com.paymybuddy.webpay.model.User;
import com.paymybuddy.webpay.model.dto.BankAccountDto;
import com.paymybuddy.webpay.model.dto.TransactionDto;
import com.paymybuddy.webpay.service.BankAccountService;
import com.paymybuddy.webpay.config.AbstractTest;


public class BankAccountTest extends AbstractTest{
	
	@Autowired
	BankAccountService bankAccountService;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IBankAccountRepository bankRepository;
	
	
	@Test
	public void getBankAccountTest() {
		
		BankAccountDto bankAccountDto = bankAccountService.getBankAccount(
				"a952e49c-54bf-4e7d-84f4-4458c7432442", "b9a85f0e-8e27-4203-a717-e241ed4c8fb9");
	
		assertTrue(bankAccountDto != null);
		assertEquals(bankAccountDto.getBankName(), "SuperBank");
	}
	
	@Test
	public void createBankAccountTest() {
		
		User user = userRepository.findByEmail("denis.wilson@mail.com");
		
		BankAccountDto bankAccountDto = new BankAccountDto();
		bankAccountDto.setBankName("BNP");
		bankAccountDto.setIban("JHOD-HDOPZ-DHOD-87908");
		
		BankAccount bankAccount = bankAccountService.createBankAccount(bankAccountDto, user.getId());
		
		assertTrue(bankAccount != null);
		assertEquals(bankAccount.getOwner().getEmail(), "denis.wilson@mail.com");
	}
	

	@Test
	public void addMoneyFromBankTest() {
		
		User user = userRepository.findOne("b9a85f0e-8e27-4203-a717-e241ed4c8fb9");
		double amountBefore = user.getCashAmount();
		TransactionDto transDto = bankAccountService.addMoneyFromBank(
				300.0, "b9a85f0e-8e27-4203-a717-e241ed4c8fb9", "3333449c-54bf-4e7d-84f4-445898362442");
		
		assertTrue(transDto != null);
		assertEquals(transDto.getCashAmount(), amountBefore + 300.0);
		
	}
	
	@Test
	public void sendMoneyToBankTest() {

		User user = userRepository.findOne("b9a85f0e-8e27-4203-a717-e241ed4c8fb9");
		double amountBefore = user.getCashAmount();
		TransactionDto transDto = bankAccountService.sendMoneyToBank(
				100.0, "b9a85f0e-8e27-4203-a717-e241ed4c8fb9", "3333449c-54bf-4e7d-84f4-445898362442");
		
		assertTrue(transDto != null);
		assertEquals(transDto.getCashAmount(), amountBefore - 100.0);
		
	}
	
	
	@Test
	public void deleteBankAccountTest() {
		
		bankAccountService.deleteBankAccount(
				"a952e49c-54bf-4e7d-84f4-4458c7432442", "b9a85f0e-8e27-4203-a717-e241ed4c8fb9");
	
		BankAccount bankAccount = bankRepository.findOne("a952e49c-54bf-4e7d-84f4-4458c7432442");
		
		assertTrue(bankAccount == null);
		
	}
	

}
