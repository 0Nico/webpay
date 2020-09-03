package com.paymybuddy.webpay.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

	private UUID id;
	private User senderUser;
	private User beneficaryUser;
	private Double cashAmount;
	private Currency currency;
	private String description;
}
