package com.paymybuddy.webpay.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.paymybuddy.webpay.dao.bankAccountDao.IBankAccountRepository;
import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.BankAccount;
import com.paymybuddy.webpay.model.User;
import com.paymybuddy.webpay.model.dto.BankAccountDto;
import com.paymybuddy.webpay.model.dto.TransactionDto;
import com.paymybuddy.webpay.model.dto.UserDto;

@Service
public class BankAccountService {

	@Autowired
	IBankAccountRepository bankRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	
	public BankAccountDto getBankAccount(String id, String userId) {
		BankAccount bankAccount = bankRepository.findOne(id);
		if (bankAccount != null) {
    		if (bankAccount.getOwner().getId().equals(userId)) {
    			
    			BankAccountDto bankDto = new BankAccountDto();
    			bankDto.setBankName(bankAccount.getBankName());
    			bankDto.setIban(bankAccount.getIban());
    			bankDto.setOwnerFirstName(bankAccount.getOwner().getFirstName());
    			bankDto.setOwnerLastName(bankAccount.getOwner().getLastName());
    			return bankDto;
    		} else {
    			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You Are Not Allowed To Request This Bank Account");
    		}
    	} else {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank Account Not Found");
    	}
    }
	
	public BankAccount createBankAccount(BankAccountDto bankAccountDto, String userId) {
		BankAccount bankAccount = new BankAccount();
		bankAccount.setBankName(bankAccountDto.getBankName());
		bankAccount.setIban(bankAccountDto.getIban());
		
		User user = userRepository.findOne(userId);
		bankAccount.setOwner(user);
		bankRepository.create(bankAccount);
		return bankAccount;
    }
    

    public void deleteBankAccount(String id, String userId) {
    	BankAccount bankAccount = bankRepository.findOne(id);
		if (bankAccount != null) {
    		if (bankAccount.getOwner().getId().equals(userId)) {
    			bankRepository.deleteById(id);
    		} else {
    			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You Are Not Allowed To Delete This Bank Account");
    		}
    	} else {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank Account Not Found");
    	}
    }

	public TransactionDto addMoneyFromBank(Double cashAmount, String userId, String accountId) {
		
		BankAccount bankAccount = bankRepository.findOne(accountId);
		if (bankAccount != null) {
    		if (bankAccount.getOwner().getId().equals(userId)) {
    			User user = userRepository.findOne(userId);
    			user.setCashAmount(user.getCashAmount() + cashAmount);
    			userRepository.update(user);
    			TransactionDto transactionDto = new TransactionDto();
    			transactionDto.setCashAmount(user.getCashAmount());
    			transactionDto.setDescription("You just add " + cashAmount
    					+ "to your WebPay account from your " + bankAccount.getBankName() 
    					+ " account : " + bankAccount.getIban() + " . Here are your new Cash Amount.");
    			return transactionDto;
    		} else {
    			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You Are Not Allowed To Add Money From This Bank Account");
    		}
    	} else {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank Account Not Found");
    	}
		
	}

	public TransactionDto sendMoneyToBank(Double cashAmount, String userId, String accountId) {
		
		BankAccount bankAccount = bankRepository.findOne(accountId);
		if (bankAccount != null) {
    		if (bankAccount.getOwner().getId().equals(userId)) {
    			User user = userRepository.findOne(userId);
    			if (user.getCashAmount() > cashAmount) {
    				user.setCashAmount(user.getCashAmount() - cashAmount);
        			userRepository.update(user);
        			TransactionDto transactionDto = new TransactionDto();
        			transactionDto.setCashAmount(user.getCashAmount());
        			transactionDto.setDescription("You just send " + cashAmount 
        					+ "to your " + bankAccount.getBankName() + " account : " + bankAccount.getIban()
        					+ " from your WebPay account . Here are your new Cash Amount.");
        			return transactionDto;
    			} else {
    				throw new ResponseStatusException(HttpStatus.CONFLICT, "You Can Not Send More Money Than You Have.");
    			}
    		} else {
    			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You Are Not Allowed To Add Money From This Bank Account");
    		}
    	} else {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank Account Not Found");
    	}	}
    	

}
