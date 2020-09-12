package com.paymybuddy.webpay.dao.bankAccountDao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.paymybuddy.webpay.model.BankAccount;

@Component
public interface IBankAccountRepository {

	BankAccount findOne(String id);

	List<BankAccount> findAll();

	void create(BankAccount bankAccount);

	BankAccount update(BankAccount bankAccount);

	void delete(BankAccount bankAccount);

	void deleteById(String id);

}
