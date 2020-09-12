package com.paymybuddy.webpay.dao.bankAccountDao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webpay.dao.transactionDao.ITransactionRepository;
import com.paymybuddy.webpay.model.BankAccount;
import com.paymybuddy.webpay.model.Transaction;

@Repository
public class BankAccountRepository implements IBankAccountRepository{

	   @PersistenceContext
	   EntityManager entityManager;
	 
	   @Transactional
	   public BankAccount findOne( String id ){
	      return entityManager.find( BankAccount.class, id );
	   }
	   
	   @Transactional
	   public List< BankAccount > findAll(){
	      return entityManager.createQuery( "from " + BankAccount.class.getName() )
	       .getResultList();
	   }
	 
	   @Transactional
	   public void create( BankAccount bankAccount ){
	      entityManager.persist( bankAccount );
	   }
	 
	   @Transactional
	   public BankAccount update( BankAccount bankAccount ){
	      return entityManager.merge( bankAccount );
	   }
	 
	   @Transactional
	   public void delete( BankAccount bankAccount ){
	      entityManager.remove( bankAccount );
	   }
	   
	   @Transactional
	   public void deleteById( String id ){
		   BankAccount bankAccount = findOne( id );
	      delete( bankAccount );
	   }
}
