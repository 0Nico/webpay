package com.paymybuddy.webpay.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.BankAccount;
import com.paymybuddy.webpay.model.Currency;
import com.paymybuddy.webpay.model.Transaction;
import com.paymybuddy.webpay.model.User;

@Service
public class UserService {

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	BankAccountService bankService;
	
	
	public void createUser(User user) {
		userRepository.create(user);
    }
 
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
 
    public User getUser(String id) {
        return userRepository.findOne(UUID.fromString(id));
    }
    
    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public void deleteUser(User user) {
    	userRepository.delete(user);
    }
    
    public List<User> addContact(User user, User newContact){
    	user.getContacts().add(newContact);
    	return user.getContacts();
    }
    
    public User getUserByEmail(String email) {
    	return getAllUser().stream().filter(user -> user.getEmail().equals(email)).findAny().orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }
    
    
   public void sendMoney(User user, User beneficiary, Double cashAmount, Currency currency, String description) {
	   
	   Transaction transaction = new Transaction();
	   transaction.setCashAmount(cashAmount);
	   transaction.setBeneficaryUser(beneficiary);
	   transaction.setSenderUser(user);
	   transaction.setDescription(description);
	   transaction.setDate(new Date());
	   transaction.setCurrency(currency);
	   
	   if(user.getCashAmount()>= cashAmount) {
		   user.setCashAmount(user.getCashAmount() - cashAmount);
		   beneficiary.setCashAmount(beneficiary.getCashAmount() + cashAmount);
		   updateUser(user);
		   updateUser(beneficiary);
		   transactionService.createTransaction(transaction);
	   }
	   
   }
   
   public void addBankAccount(User user, String iban, String bankName) {
	   
	   BankAccount bankAccount = new BankAccount();
	   bankAccount.setIban(iban);
	   bankAccount.setBankName(bankName);
	   bankAccount.setOwner(user);
	   bankService.createBankAccount(bankAccount);
	   
	   user.setBankAccount(bankAccount);
	   updateUser(user);
   }
    
}
