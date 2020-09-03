package com.paymybuddy.webpay.dao.bankAccountDao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.paymybuddy.webpay.dao.transactionDao.ITransactionRepository;
import com.paymybuddy.webpay.model.BankAccount;
import com.paymybuddy.webpay.model.Transaction;

@Repository
public class BankAccountRepository implements IBankAccountRepository{

	   @PersistenceContext
	   EntityManager entityManager;
	 
	   @Override
	   public BankAccount findOne( UUID id ){
	      return entityManager.find( BankAccount.class, id );
	   }
	   
	   @Override
	   public List< BankAccount > findAll(){
	      return entityManager.createQuery( "from " + BankAccount.class.getName() )
	       .getResultList();
	   }
	 
	   @Override
	   public void create( BankAccount bankAccount ){
	      entityManager.persist( bankAccount );
	   }
	 
	   @Override
	   public BankAccount update( BankAccount bankAccount ){
	      return entityManager.merge( bankAccount );
	   }
	 
	   @Override
	   public void delete( BankAccount bankAccount ){
	      entityManager.remove( bankAccount );
	   }
	   
	   @Override
	   public void deleteById( UUID id ){
		   BankAccount bankAccount = findOne( id );
	      delete( bankAccount );
	   }
}
