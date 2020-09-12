package com.paymybuddy.webpay.web;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.webpay.model.Transaction;
import com.paymybuddy.webpay.model.dto.TransactionDto;
import com.paymybuddy.webpay.model.dto.UserDto;
import com.paymybuddy.webpay.service.CustomUserDetailsService;
import com.paymybuddy.webpay.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	
	@GetMapping(path = "/list", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<TransactionDto> getAllTransactionsByUser () {
		
		return transactionService.getTransactionsByUser(userDetailsService.currentUser().getId());
	}
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public TransactionDto getTransaction (@RequestParam("id") String id) {
		
		return transactionService.getTransaction(id, userDetailsService.currentUser().getId());
	}
	
	
	@PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDto executeMoneyTransfer (@RequestBody TransactionDto transactionDto) {
		
		return transactionService.sendMoney(userDetailsService.currentUser().getId(), transactionDto);
	}

}
