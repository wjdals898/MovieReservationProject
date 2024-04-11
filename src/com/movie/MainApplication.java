package com.movie;

import java.util.Scanner;

import com.movie.controller.MovieController;
import com.movie.controller.UserController;
import com.movie.dto.UserDTO;
import com.movie.service.UserService;
import com.movie.view.UserView;

public class MainApplication {

	static Scanner sc = new Scanner(System.in);
	public static UserDTO guest = new UserDTO("게스트");
	public static UserDTO user = guest;

	public static void main(String[] args) {
		boolean isStop = false;

		while (!isStop) {
			int select = menuPrint();
			switch (select) {
			case 1 -> { // 로그인
				user = UserController.loginMenu(sc);

				boolean result = UserView.print(user);
				if (result) { // 로그인 성공한 경우 회원 메뉴로 전환
					if (user.isManager()) { // 관리자 계정일 경우 관리자 메뉴로 전환
						UserController.managementMenu(sc);
					} else {
						MovieController.movieMainMenu(sc); // 회원전용 메뉴
					}
				}
				user = guest;
			}
			case 2 -> { // 회원가입
				int result = UserController.SignUpMenu(sc);
				UserView.print(result);
			}
			case 3 -> {
				MovieController.movieMenu(sc); // 비회원전용 메뉴
			}
			case 4 -> {
				isStop = true;
			}
			}
		}
		System.out.println("=============<서비스를 종료합니다>=============");
		sc.close();

	}

	private static int menuPrint() {
		System.out.println("=========================================");
		System.out.println("||              영화 예매 서비스            ||");
		System.out.println("=========================================");
		System.out.println("1.로그인 | 2.회원가입 | 3.영화조회 | 4.종료");
		System.out.print("작업을 선택하세요>> ");
		int job = Integer.parseInt(sc.nextLine());

		return job;
	}

}
