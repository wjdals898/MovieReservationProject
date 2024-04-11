package com.movie.controller;

import java.util.List;
import java.util.Scanner;

import com.movie.MainApplication;
import com.movie.dto.MovieDTO;
import com.movie.dto.UserDTO;
import com.movie.service.MovieService;
import com.movie.service.TheaterService;
import com.movie.service.UserService;
import com.movie.view.MovieView;

public class UserController {

	static UserService userService = new UserService();
	static MovieService movieService = new MovieService();

	public static UserDTO loginMenu(Scanner sc) {
		System.out.println("\n[로그인]");
		System.out.print("아이디>> ");
		String username = sc.nextLine();

		System.out.print("패스워드>> ");
		String password = sc.nextLine();
		System.out.println();

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
		System.out.println();

		return userService.signup(username, password, nickname);
	}

	public static void managementMenu(Scanner sc) { // 관리자 전용 메뉴
		// 관리자의 경우 상영관 추가 가능
		boolean isLogout = false;
		while (!isLogout) {
			int select = managerMenu(sc);
			switch (select) {
			case 1 -> { // 영화 추가
				MovieController.addMovieMenu(sc);
			}
			case 2 -> { // 상영관 추가
				while (true) {
					List<MovieDTO> movieList = movieService.showAll();
					MovieView.printAll(movieList, "영화 리스트");
					if (movieList.size() <= 0) {
						System.out.println("<데이터가 없습니다. 먼저 영화 정보를 추가하세요.>\n");
					} else {
						int result = subMenu(sc, movieList);
						if(result == 1)
							break;
					}
				}
			}
			case 3 -> {
				isLogout = true;
			}
			}
		}
	}

	private static int subMenu(Scanner sc, List<MovieDTO> movieList) {
		int result = -1;
		int movieNum = 0;
		while (true) {
			System.out.println("-----------------------------------------");
			System.out.print("상영관을 추가할 영화번호를 선택하세요>> ");
			movieNum = Integer.parseInt(sc.nextLine());
			if (movieNum <= 0 || movieNum > movieList.size()) {
				System.out.println("<잘못된 접근입니다!>\n");
				continue;
			} else {
				break;
			}
		}
		result = TheaterController.addTheaterMenu(sc, movieList.get(movieNum-1));
		return result;
	}

	public static int managerMenu(Scanner sc) {
		System.out.println("=========================================");
		System.out.println("* " + MainApplication.user.getNickname() + " 관리자님 *");
		System.out.println("=========================================");
		System.out.println("1.영화추가 | 2.상영관추가 | 3.로그아웃");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());
		System.out.println();

		return select;
	}

}
