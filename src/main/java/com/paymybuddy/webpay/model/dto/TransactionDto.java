package com.paymybuddy.webpay.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class TransactionDto {

	private String beneficiaryEmail;
	private String senderEmail;
	private double cashAmount;
	private String currency;
	private String description;
	
}
