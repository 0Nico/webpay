package com.paymybuddy.webpay.dao.transactionDao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.paymybuddy.webpay.model.Transaction;

@Repository
public class TransactionRepository implements ITransactionRepository{

	   @PersistenceContext
	   EntityManager entityManager;
	 
	   @Override
	   public Transaction findOne( UUID id ){
	      return entityManager.find( Transaction.class, id );
	   }
	   
	   @Override
	   public List< Transaction > findAll(){
	      return entityManager.createQuery( "from " + Transaction.class.getName() )
	       .getResultList();
	   }
	 
	   @Override
	   public void create( Transaction transaction ){
	      entityManager.persist( transaction );
	   }
	 
	   @Override
	   public Transaction update( Transaction transaction ){
	      return entityManager.merge( transaction );
	   }
	 
	   @Override
	   public void delete( Transaction transaction ){
	      entityManager.remove( transaction );
	   }
	   
	   @Override
	   public void deleteById( UUID id ){
		   Transaction transaction = findOne( id );
	      delete( transaction );
	   }

	   
	   @Override
	   public List<Transaction> findByUser(UUID id) {
		   Query q = entityManager.createQuery("select t from Transaction t where t.senderUser.id = :id or t.beneficiaryUser.id = :id order by t.date asc");
		   q.setParameter("id", id);
		   List<Transaction> results = q.getResultList();
		   return results;
	   }
}
