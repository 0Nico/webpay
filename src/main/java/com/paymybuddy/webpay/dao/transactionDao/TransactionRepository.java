package com.paymybuddy.webpay.dao.transactionDao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webpay.model.Transaction;

@Repository
public class TransactionRepository implements ITransactionRepository{

	   @PersistenceContext
	   EntityManager entityManager;
	 
	   @Transactional
	   public Transaction findOne( String id ){
	      return entityManager.find( Transaction.class, id );
	   }
	   
	   @Transactional
	   public List< Transaction > findAll(){
	      return entityManager.createQuery( "from " + Transaction.class.getName() )
	       .getResultList();
	   }
	 
	   @Transactional
	   public void create( Transaction transaction ){
	      entityManager.persist( transaction );
	   }
	 
	   @Transactional
	   public Transaction update( Transaction transaction ){
	      return entityManager.merge( transaction );
	   }
	 
	   @Transactional
	   public void delete( Transaction transaction ){
	      entityManager.remove( transaction );
	   }
	   
	   @Transactional
	   public void deleteById( String id ){
		   Transaction transaction = findOne( id );
	      delete( transaction );
	   }

	   
	   @Transactional
	   public List<Transaction> findByUser(String id) {
		   Query q = entityManager.createQuery("select t from Transaction t where t.senderUser.id = :id or t.beneficiaryUser.id = :id order by t.date asc");
		   q.setParameter("id", id);
		   List<Transaction> results = q.getResultList();
		   return results;
	   }
}
