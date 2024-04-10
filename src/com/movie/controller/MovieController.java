package com.movie.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.movie.MainApplication;
import com.movie.dto.MovieDTO;
import com.movie.dto.UserDTO;
import com.movie.service.MovieService;
import com.movie.view.MovieView;

public class MovieController {

	static MovieService movieService = new MovieService();
	static UserDTO user = MainApplication.user;

	public static void movieMainMenu(Scanner sc) { // 로그인한 회원 메뉴
		boolean isLogout = false;

		while (!isLogout) {
			int select = mainMenu(sc);

			switch (select) {
			case 1 -> { // 1. 전체영화조회
				List<MovieDTO> movieList = movieService.showAll();
				while (true) {
					MovieView.printAll(movieList, "전체영화 리스트");
					int result = subMenu(sc, movieList);
					if (result == 1 || result == 0)
						break;
					else
						continue;
				}
			}
			case 2 -> {
				List<MovieDTO> movieList = movieService.showByScreening();
				while (true) {
					MovieView.printScreening(movieList);
					int result = subMenu(sc, movieList);
					if (result == 1 || result == 0)
						break;
					else
						continue;
				}
			}
			case 3 -> {
				System.out.print("영화제목 입력>> ");
				String title = sc.nextLine();
				List<MovieDTO> movieList = movieService.searchByTitle(title);
				while (true) {
					MovieView.printAll(movieList, title + " 검색결과 (" + movieList.size() + "건)");
					int result = subMenu(sc, movieList);
					if (result == 1 || result == 0)
						break;
					else
						continue;
				}
			}
			case 4 -> {

			}
			case 5 -> {
				isLogout = true;
			}
			}
		}
	}

	private static int mainMenu(Scanner sc) {
		System.out.println("===================================");
		System.out.println(user.getNickname() + "님");
		System.out.println("===================================");
		System.out.println("1.전체영화조회 | 2.상영중인영화조회 | 3.영화검색 | 4.예매내역 | 5.로그아웃");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());

		return select;
	}

	private static int subMenu(Scanner sc, List<MovieDTO> movieList) {

		System.out.println("-----------------------------------------");
		System.out.println("1.영화선택 | 2.뒤로가기(메인으로)");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());
		int result;

		if (select == 1) {
			int movieNum;
			while (true) {
				System.out.print("영화번호 입력>> ");
				movieNum = Integer.parseInt(sc.nextLine());
				if (movieNum <= 0 || movieNum > movieList.size()) {
					System.out.println("<잘못된 접근입니다!>");
					continue;
				} else {
					break;
				}
			}
			result = TheaterController.selectTheaterMenu(sc, movieList.get(movieNum - 1))==0?-1:1;	// 1이면 홈으로 0이면 이전으로
		} else if (select == 2) {
			result = 0; // 뒤로가기(로그인회원 메인메뉴로)
		} else {
			System.out.println("<잘못된 접근입니다!>");
			result = -1; // 이 함수 다시 호출
		}

		return result; // 영화 선택 완료
	}

}
