package com.paymybuddy.webpay.dao.userDao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.webpay.model.Transaction;
import com.paymybuddy.webpay.model.User;

@Repository
public class UserRepository implements IUserRepository{

	   @PersistenceContext
	   EntityManager entityManager;
	 
	   @Transactional
	   public User findOne( String id ){
	      return entityManager.find( User.class, id );
	   }
	   
	   @Transactional
	   public List< User > findAll(){
	      return entityManager.createQuery( "from " + User.class.getName() )
	       .getResultList();
	   }
	 
	   @Transactional
	   public void create( User user ){
	      entityManager.persist( user );
	   }
	 
	   @Transactional
	   public User update( User user ){
	      return entityManager.merge( user );
	   }
	 
	   @Transactional
	   public void delete( User user ){
	      entityManager.remove( user );
	   }
	   
	   @Transactional
	   public void deleteById( String id ){
	      User user = findOne( id );
	      delete( user );
	   }

	   @Transactional
	public User findByEmail(String email) {
		Query q = entityManager.createQuery("select u from User u where u.email = :email");
		q.setParameter("email", email);
	    List<User> result = q.getResultList();
	    if (result.isEmpty()) {
	          return null;
	      }
	    return result.get(0);
	      
	}
	
}
