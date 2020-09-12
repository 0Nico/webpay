package com.paymybuddy.webpay.dao.transactionDao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Component;
import com.paymybuddy.webpay.model.Transaction;

@Component
public interface ITransactionRepository {

	void deleteById(String id);

	void delete(Transaction transaction);

	Transaction update(Transaction transaction);

	void create(Transaction transaction);

	List<Transaction> findAll();

	Transaction findOne(String id);
	
	public List<Transaction> findByUser(@Param("id") String idsender);
	

}
