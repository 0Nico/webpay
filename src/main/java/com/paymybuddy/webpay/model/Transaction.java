package com.paymybuddy.webpay.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
	private UUID id;
	
	@ManyToOne
	private User senderUser;
	
	@ManyToOne
	private User beneficiaryUser;
	
	private Double cashAmount;
	private Currency currency;
	private String description;
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date date;
}
