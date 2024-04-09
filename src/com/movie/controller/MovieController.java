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
				MovieView.printAll(movieList, "전체영화 리스트");
				subMenu(sc, movieList);

			}
			case 2 -> {
				List<MovieDTO> movieList = movieService.showByScreening();
				MovieView.printScreening(movieList);
				subMenu(sc, movieList);
//				if(subSelect == 1) {
//					System.out.print("영화번호 입력>> ");
//					int movieId = Integer.parseInt(sc.nextLine());
//					TheaterController.selectTheaterMenu(sc, movieId);
//				} else if(subSelect == 2) {
//					continue;
//				} else {
//					System.out.println("<잘못된 접근입니다!>");
//				}
			}
			case 3 -> {
				System.out.print("영화제목 입력>> ");
				String title = sc.nextLine();
				List<MovieDTO> movieList = movieService.searchByTitle(title);
				MovieView.printAll(movieList, title + " 검색결과 (" + movieList.size() + "건)");
				subMenu(sc, movieList);
//				if(subSelect == 1) {
//					
//				} else if(subSelect == 2) {
//					continue;
//				} else {
//					System.out.println("<잘못된 접근입니다!>");
//				}
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

	private static void subMenu(Scanner sc, List<MovieDTO> movieList) {
		
		while (true) {
			System.out.println("-----------------------------------------");
			System.out.println("1.영화선택 | 2.뒤로가기");
			System.out.print("서비스를 선택하세요>> ");
			int select = Integer.parseInt(sc.nextLine());
			
			if (select == 1) {
				System.out.print("영화번호 입력>> ");
				int movieNum = Integer.parseInt(sc.nextLine());
				if (movieNum <= 0 || movieNum > movieList.size()) {
					System.out.println("<잘못된 접근입니다!>");
					continue;
				}
				TheaterController.selectTheaterMenu(sc, movieList.get(movieNum - 1));
			} else if (select == 2) {
				return;
			} else {
				System.out.println("<잘못된 접근입니다!>");
				continue;
			}
		}
	}

}
