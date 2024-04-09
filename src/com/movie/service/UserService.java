package com.movie.service;

import com.movie.model.UserDAO;

public class UserService {
	
	UserDAO userDao = new UserDAO();
	
	public String login(String username, String password) {
		return userDao.login(username, password);
	}

}
