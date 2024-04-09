package com.movie.controller;

import java.util.Scanner;

import com.movie.service.UserService;

public class UserController {
	
	static UserService userService = new UserService();

	public static String loginMenu(Scanner sc) {
		System.out.println("[로 그 인]");
		System.out.print("아이디>> ");
		String username = sc.next();
		
		System.out.print("패스워드>> ");
		String password = sc.next();
		
		return userService.login(username, password);
	}

	
}
