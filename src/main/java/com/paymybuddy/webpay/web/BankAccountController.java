package com.paymybuddy.webpay.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.webpay.model.BankAccount;
import com.paymybuddy.webpay.model.dto.BankAccountDto;
import com.paymybuddy.webpay.model.dto.TransactionDto;
import com.paymybuddy.webpay.model.dto.UserDto;
import com.paymybuddy.webpay.service.BankAccountService;
import com.paymybuddy.webpay.service.CustomUserDetailsService;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {

	@Autowired
	BankAccountService bankAccountService;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public BankAccountDto getBankAccount (@RequestParam("id") String id) {
		
		return bankAccountService.getBankAccount(id, userDetailsService.currentUser().getId());
	}

	
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public BankAccount createBankAccount (@RequestBody BankAccountDto bankAccountDto) {
		
		return bankAccountService.createBankAccount(bankAccountDto, userDetailsService.currentUser().getId());
	}
	
	@DeleteMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public void deleteBankAccount (@RequestParam("id") String id) {
		
		bankAccountService.deleteBankAccount(id, userDetailsService.currentUser().getId());
	}
	
	
	@PostMapping(path = "/addMoneyFromBank", produces=MediaType.APPLICATION_JSON_VALUE)
	public TransactionDto addMoneyFromBankAccount (@RequestParam("cashAmount") Double cashAmount, @RequestParam("accountId") String accountId) {
		
		return bankAccountService.addMoneyFromBank(cashAmount, userDetailsService.currentUser().getId(), accountId);
	}
	
	
	@PostMapping(path = "/sendMoneyToBank", produces=MediaType.APPLICATION_JSON_VALUE)
	public TransactionDto sendMoneyToBankAccount (@RequestParam("cashAmount") Double cashAmount, @RequestParam("accountId") String accountId) {
		
		return bankAccountService.sendMoneyToBank(cashAmount, userDetailsService.currentUser().getId(), accountId);
	}
	
}
