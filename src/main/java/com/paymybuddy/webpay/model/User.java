package com.paymybuddy.webpay.model;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private UUID id;
	private String lastName;
	private String firstName;
	private String email;
	private String password;
	private Double cashAmount;
	private List<User> contacts;
	private BankAccount bankAccount;
	private List<Transaction> transactions;
	
}
