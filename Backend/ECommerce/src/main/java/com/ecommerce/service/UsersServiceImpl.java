package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dao.UsersDAO;
import com.ecommerce.model.Image;
import com.ecommerce.model.LoginApiResponse;
import com.ecommerce.model.RegisterApiResponse;

@Service
public class UsersServiceImpl implements UsersService{

	@Autowired
	UsersDAO userDAO;
	
	@Override
	public RegisterApiResponse register(String name, String email, String password, String gender, int age,
			String image) {
		return userDAO.register(name, email, password, gender, age, image);
	}

	@Override
	public LoginApiResponse login(String email, String password) {
		return userDAO.login(email, password);
	}

	@Override
	public void deleteUser(int userId) {
		userDAO.deleteUser(userId);
		
	}

	@Override
	public void updatePassword(int userId, String password) {
		userDAO.updatePassword(userId, password);
	}

	@Override
	public void updateImage(int userId, String image) {
		userDAO.updateImage(userId, image);
	}

	@Override
	public Image getImage(int userId) {
		return userDAO.getImage(userId);
	}

}
