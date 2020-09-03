package com.paymybuddy.webpay.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webpay.dao.bankAccountDao.IBankAccountRepository;
import com.paymybuddy.webpay.model.BankAccount;

@Service
public class BankAccountService {

	@Autowired
	IBankAccountRepository bankRepository;
	
	public void createBankAccount(BankAccount bankAccount) {
		bankRepository.create(bankAccount);
    }
 
    public List<BankAccount> getAllBankAccount() {
        return bankRepository.findAll();
    }
 
    public BankAccount getBankAccount(String id) {
        return bankRepository.findOne(UUID.fromString(id));
    }
    
    public BankAccount updateBankAccount(BankAccount bankAccount) {
        return bankRepository.update(bankAccount);
    }

    public void deleteTransaction(BankAccount bankAccount) {
    	bankRepository.delete(bankAccount);
    }
}
