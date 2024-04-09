package com.movie.controller;

import java.util.Scanner;

import com.movie.dto.UserDTO;
import com.movie.service.UserService;

public class UserController {
	
	static UserService userService = new UserService();

	public static UserDTO loginMenu(Scanner sc) {
		System.out.println("[로그인]");
		System.out.print("아이디>> ");
		String username = sc.nextLine();
		
		System.out.print("패스워드>> ");
		String password = sc.nextLine();
		
		return userService.login(username, password);
	}

	public static int SignUpMenu(Scanner sc) {
		System.out.println("[회원가입]");
		System.out.print("아이디>> ");
		String username = sc.nextLine();
		
		System.out.print("비밀번호>> ");
		String password = sc.nextLine();
		
		System.out.print("닉네임>> ");
		String nickname = sc.nextLine();
		
		return userService.signup(username, password, nickname);
	}

	
}
