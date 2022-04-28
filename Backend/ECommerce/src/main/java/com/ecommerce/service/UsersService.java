package com.ecommerce.service;

import com.ecommerce.model.Image;
import com.ecommerce.model.LoginApiResponse;
import com.ecommerce.model.RegisterApiResponse;

public interface UsersService {

	RegisterApiResponse register(String name,String email,String password,String gender,int age,String image);
	
	LoginApiResponse login(String email, String password);
	
	void deleteUser(int userId);
	
	void updatePassword(int userId, String password);
	
	void updateImage(int userId, String image);
	
	Image getImage(int userId);
}
