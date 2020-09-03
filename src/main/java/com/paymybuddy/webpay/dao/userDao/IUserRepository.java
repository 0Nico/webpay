package com.paymybuddy.webpay.dao.userDao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.paymybuddy.webpay.model.User;

@Component
public interface IUserRepository {

	User findOne(UUID id);

	List<User> findAll();

	void create(User user);

	User update(User user);

	void delete(User user);

	void deleteById(UUID id);

}
