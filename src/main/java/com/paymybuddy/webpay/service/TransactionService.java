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
	
	@Autowired
	UserService userService;
	
 
    public TransactionDto getTransaction(UUID id, UUID userId) {
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
    
    
    public List<TransactionDto> getTransactionsByUser(UUID id){
    	
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
    
    
    public UserDto sendMoney(User user, TransactionDto transactionDto) {
 	   
 	   Transaction transaction = new Transaction();
 	   transaction.setCashAmount(transactionDto.getCashAmount());
 	   User beneficiary = userRepository.findByEmail(transactionDto.getBeneficiaryEmail());
 		   	if(beneficiary!= null) {
 		   		transaction.setBeneficiaryUser(beneficiary);
 		   	} else {
 		   		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Beneficiary Not Found");
 		   	}
 	   transaction.setSenderUser(user);
 	   transaction.setDescription(transactionDto.getDescription());
 	   transaction.setDate(new Date());
 	   transaction.setCurrency(Currency.valueOf(transactionDto.getCurrency()));
 	   
 	   if(user.getCashAmount()>= transaction.getCashAmount()) {
 		   user.setCashAmount(user.getCashAmount() - transaction.getCashAmount());
 		   beneficiary.setCashAmount(beneficiary.getCashAmount() + transaction.getCashAmount());
 		   userService.updateUser(user);
 		   userService.updateUser(beneficiary);
 		   transactionRepository.create(transaction);
 	   } else {
 		   throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Sender Could Not Send More Money Than He Have. Please Retry with a smaller amount.");
 	   }
 	   UserDto userDto = new UserDto();
 	   userDto.setCashAmount(user.getCashAmount());
 	   return userDto;
    }
    
}
