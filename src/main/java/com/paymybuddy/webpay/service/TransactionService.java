package com.paymybuddy.webpay.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.paymybuddy.webpay.dao.transactionDao.ITransactionRepository;
import com.paymybuddy.webpay.dao.userDao.IUserRepository;
import com.paymybuddy.webpay.model.Currency;
import com.paymybuddy.webpay.model.Transaction;
import com.paymybuddy.webpay.model.User;
import com.paymybuddy.webpay.model.dto.TransactionDto;
import com.paymybuddy.webpay.model.dto.UserDto;

@Service
public class TransactionService {

	@Autowired
	ITransactionRepository transactionRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	
 
    public TransactionDto getTransaction(String id, String userId) {
    	Transaction transaction = transactionRepository.findOne(id);
    	if (transaction != null) {
    		if (transaction.getBeneficiaryUser().getId().equals(userId) || transaction.getSenderUser().getId().equals(userId)) {
    			
    			TransactionDto transaDto = new TransactionDto();
        		transaDto.setBeneficiaryEmail(transaction.getBeneficiaryUser().getEmail());
        		transaDto.setCashAmount(transaction.getCashAmount());
        		transaDto.setCurrency(transaction.getCurrency().toString());
        		transaDto.setDescription(transaction.getDescription());
        		transaDto.setSenderEmail(transaction.getSenderUser().getEmail());
    			return transaDto;
    		} else {
    			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You Are Not Allowed To Request This Transaction");
    		}
    	} else {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction Not Found");
    	}
        
    }
    
    
    public List<TransactionDto> getTransactionsByUser(String id){
    	
    	List<TransactionDto> transactionDtoList = new ArrayList<TransactionDto>();
    	transactionRepository.findByUser(id).stream().forEach(transa -> {
    		
    		TransactionDto transaDto = new TransactionDto();
    		transaDto.setBeneficiaryEmail(transa.getBeneficiaryUser().getEmail());
    		transaDto.setCashAmount(transa.getCashAmount());
    		transaDto.setCurrency(transa.getCurrency().toString());
    		transaDto.setDescription(transa.getDescription());
    		transaDto.setSenderEmail(transa.getSenderUser().getEmail());
    		
    		transactionDtoList.add(transaDto);
    	});
    	return transactionDtoList;
    }
    
    
    public UserDto sendMoney(String idUser, TransactionDto transactionDto) {
 	   
    	User beneficiary = userRepository.findByEmail(transactionDto.getBeneficiaryEmail());
    	if( beneficiary != null) {
	    	
	    	User user = userRepository.findOne(idUser);
	    	if(user.getCashAmount()>= transactionDto.getCashAmount()) {
	    		
	    		User facturationUser = userRepository.findByEmail("webpay.facturation@mail.com");
	    		facturationUser.setCashAmount(facturationUser.getCashAmount() + transactionDto.getCashAmount() * 0.05);
	    		
	    		user.setCashAmount(user.getCashAmount() - transactionDto.getCashAmount());
	 		    beneficiary.setCashAmount(beneficiary.getCashAmount() + transactionDto.getCashAmount() * 0.95);
	 		    userRepository.update(user);
	 		    userRepository.update(beneficiary);
	 		    userRepository.update(facturationUser);
	 		    
		    	Transaction transaction = new Transaction();
		 	    transaction.setCashAmount(transactionDto.getCashAmount());
			   	transaction.setBeneficiaryUser(beneficiary);
		 	    transaction.setSenderUser(user);
		 	    transaction.setDescription(transactionDto.getDescription());
		 	    transaction.setDate(new Date());
		 	    transaction.setCurrency(Currency.valueOf(transactionDto.getCurrency()));		 	    
	 	    	
	 		    transactionRepository.create(transaction);
		 	   
		 	    UserDto userDto = new UserDto();
		 	    userDto.setCashAmount(user.getCashAmount());
		 	    return userDto;
	    	 } else {
		 		    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sender Could Not Send More Money Than He Have. Please Retry with a smaller amount.");
		 	    }
    	} else {
	   		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beneficiary Not Found");
	   	}
    }
    
}
