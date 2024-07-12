package com.movie.view;

import com.movie.dto.UserDTO;

public class UserView {

	public static boolean print(UserDTO user) {
		boolean result = false;
		System.out.println("-----------------------------------------");
		if(user.getId() != 0) {
			System.out.println("[로그인성공] "+user.getNickname()+"님 환영합니다.");
			result = true;
		} else {
			System.out.println("[로그인실패] 잘못된 아이디/패스워드입니다.");
		}
		System.out.println("-----------------------------------------\n");
		return result;
	}

	public static void print(int result) {
		String str;
		System.out.println("-----------------------------------------");
		if(result == 1) {
			str = "[회원가입 성공]";
		} else {
			str = "[회원가입 실패] 이미 존재하는 아이디/닉네임입니다.";
		}
		System.out.println(str);
		System.out.println("-----------------------------------------\n");
	}
}
