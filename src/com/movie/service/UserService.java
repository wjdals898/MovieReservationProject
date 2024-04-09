package com.movie.service;

import com.movie.dto.UserDTO;
import com.movie.model.UserDAO;

public class UserService {
	
	UserDAO userDao = new UserDAO();
	
	public UserDTO login(String username, String password) {
		return userDao.login(username, password);
	}

	public int signup(String username, String password, String nickname) {
		return userDao.signup(username, password, nickname);
	}

}
