package com.movie.view;

public class UserView {

	public static String print(String nickname) {
		String result;
		if(nickname != null) {
			result = "*****"+nickname+"님 환영합니다.*****\n";
		} else {
			result = "[로그인실패] 잘못된 아이디/패스워드입니다.\n";
		}
		return result;
	}
}
