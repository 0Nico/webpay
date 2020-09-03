package com.paymybuddy.webpay.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccount {

	private UUID id;
	private User owner;
	private String iban;
	private String bankName;
}
