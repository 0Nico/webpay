package com.paymybuddy.webpay.dao.userDao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.paymybuddy.webpay.model.Transaction;
import com.paymybuddy.webpay.model.User;

@Repository
public class UserRepository implements IUserRepository{

	   @PersistenceContext
	   EntityManager entityManager;
	 
	   @Override
	   public User findOne( UUID id ){
	      return entityManager.find( User.class, id );
	   }
	   
	   @Override
	   public List< User > findAll(){
	      return entityManager.createQuery( "from " + User.class.getName() )
	       .getResultList();
	   }
	 
	   @Override
	   public void create( User user ){
	      entityManager.persist( user );
	   }
	 
	   @Override
	   public User update( User user ){
	      return entityManager.merge( user );
	   }
	 
	   @Override
	   public void delete( User user ){
	      entityManager.remove( user );
	   }
	   
	   @Override
	   public void deleteById( UUID id ){
	      User user = findOne( id );
	      delete( user );
	   }

	@Override
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
