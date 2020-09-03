package com.paymybuddy.webpay.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.webpay.dao.transactionDao.ITransactionRepository;
import com.paymybuddy.webpay.model.Transaction;

@Service
public class TransactionService {

	@Autowired
	ITransactionRepository transactionRepository;
	
	public void createTransaction(Transaction transaction) {
		transactionRepository.create(transaction);
    }
 
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }
 
    public Transaction getTransaction(String id) {
        return transactionRepository.findOne(UUID.fromString(id));
    }
    
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.update(transaction);
    }

    public void deleteTransaction(Transaction transaction) {
		transactionRepository.delete(transaction);
    }
    
    
    public List<Transaction> getTransactionsBySender(String id){
    	return transactionRepository.findBySender_Id(UUID.fromString(id));
    }
    
    public List<Transaction> getTransactionsByBeneficiary(String id){
    	return transactionRepository.findByBeneficiary_Id(UUID.fromString(id));
    }
}
