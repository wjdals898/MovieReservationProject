package com.movie;

import java.util.Scanner;

import com.movie.controller.UserController;
import com.movie.view.UserView;

public class MainApplication {
	
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		boolean isStop = false;
		String nickname;
		
		while(!isStop) {
			int select = menuPrint();
			switch(select) {
			case 1->{
				nickname = UserController.loginMenu(sc);
				System.out.println(UserView.print(nickname));
			}
			case 2->{
				
			}
			case 3->{
				
			}
			case 4->{
				isStop = true;
			}
			}
		}
		System.out.println("-----------서비스를 종료합니다.-----------");
		sc.close();

	}

	private static int menuPrint() {
		System.out.println("===================================");
		System.out.println("||           영화 예매 서비스          ||");
		System.out.println("===================================");
		System.out.println("1.로그인 | 2.회원가입 | 3.영화조회 | 4.종료");
		System.out.print("작업을 선택하세요>> ");
		int job = sc.nextInt();
		System.out.println("-----------------------------------");
		
		return job;
	}

}
