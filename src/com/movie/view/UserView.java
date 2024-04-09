package com.movie.view;

public class UserView {

	public static boolean print(String nickname) {
		boolean result = false;
		if(nickname != null) {
			System.out.println("********"+nickname+"님 환영합니다.********\n");
			result = true;
		} else {
			System.out.println("[로그인실패] 잘못된 아이디/패스워드입니다.\n");
		}
		return result;
	}

	public static void print(int result) {
		String str;
		if(result == 1) {
			str = "[회원가입 성공]\n";
		} else {
			str = "[회원가입 실패] 이미 존재하는 아이디/닉네임입니다.\n";
		}
		System.out.println(str);
	}
}
