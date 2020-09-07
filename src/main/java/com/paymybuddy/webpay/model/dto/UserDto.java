package com.paymybuddy.webpay.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class UserDto {

	private String firstName;
	private String lastName;
	private double cashAmount;
	private String email;
}
