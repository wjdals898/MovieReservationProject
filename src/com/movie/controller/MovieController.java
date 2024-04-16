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

	public static void movieMainMenu(Scanner sc) { // 로그인한 회원 메뉴
		
		boolean isLogout = false;

		while (!isLogout) {
			int select = loginUserMainMenu(sc);
			List<MovieDTO> movieList = null;

			switch (select) {
			case 1 -> { // 1. 전체영화조회
				movieList = movieService.showAll();
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
				movieList = movieService.showByScreening();
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
				movieList = movieService.searchByTitle(title);
				while (true) {
					MovieView.printAll(movieList, "\""+title + "\" 검색결과 (" + movieList.size() + "건)");
					int result = subMenu(sc, movieList);
					if (result == 1 || result == 0)
						break;
					else
						continue;
				}
			}
			case 4 -> { // 예매내역 확인
				System.out.println();
				int result = ReservationController.showReservationMenu(sc);

			}
			case 5 -> {
				isLogout = true;
			}
			default ->{
				System.out.println("<잘못된 접근입니다!>\n");
			}
			}
		}
	}

	public static void movieMenu(Scanner sc) {
		boolean isStop = false;

		while (!isStop) {
			int select = noLoginMainMenu(sc);
			List<MovieDTO> movieList = null;

			switch (select) {
			case 1 -> {
				movieList = movieService.showAll();
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
				movieList = movieService.showByScreening();
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
				movieList = movieService.searchByTitle(title);
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
				isStop = true;
			}
			default ->{System.out.println("<잘못된 접근입니다!>\n");}
			}
		}

	}

	private static int loginUserMainMenu(Scanner sc) {
		UserDTO user = MainApplication.user;
		System.out.println("=========================================");
		System.out.println("* " + user.getNickname() + "님 *");
		System.out.println("=========================================");
		System.out.println("1.전체영화조회 | 2.상영중인영화조회 | 3.영화검색 | 4.예매내역 | 5.로그아웃");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());

		return select;
	}

	private static int noLoginMainMenu(Scanner sc) {
		System.out.println("=========================================");
		System.out.println("* 게스트님 *");
		System.out.println("=========================================");
		System.out.println("1.전체영화조회 | 2.상영중인영화조회 | 3.영화검색 | 4.뒤로가기");
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
					System.out.println("<잘못된 접근입니다!>\n");
					continue;
				} else {
					System.out.println();
					break;
				}
			}
			result = TheaterController.selectTheaterMenu(sc, movieList.get(movieNum - 1)) == 0 ? -1 : 1; // 1이면 홈으로 0이면
																											// 이전으로
		} else if (select == 2) {
			result = 0; // 뒤로가기(로그인회원 메인메뉴로)
		} else {
			System.out.println("<잘못된 접근입니다!>\n");
			result = -1; // 이 함수 다시 호출
		}

		return result; // 영화 선택 완료
	}

	public static void addMovieMenu(Scanner sc) { // 관리자 전용 메뉴
		System.out.println("-----------------------------------------");
		System.out.println("추가할 영화 정보를 입력하세요");
		System.out.print(" 제  목 | ");
		String title = sc.nextLine();
		System.out.print(" 감  독 | ");
		String director = sc.nextLine();
		System.out.print(" 상영시간 | ");
		String runningTime = sc.nextLine();
		System.out.print(" 소  개 | ");
		String content = sc.nextLine();
		System.out.println("-----------------------------------------");
		while (true) {
			System.out.println("1.추가하기 | 2.뒤로가기");
			System.out.print("서비스를 선택하세요>> ");
			int select = Integer.parseInt(sc.nextLine());
			if (select == 1) {
				MovieDTO newMovie = new MovieDTO();
				newMovie.setMovieTitle(title);
				newMovie.setDirector(director);
				newMovie.setRunningTime(runningTime);
				newMovie.setContent(content);
				
				int result = movieService.addMovie(newMovie); // 1이면 추가성공 0이면 실패
				if(result == 1) {
					System.out.println("[추가완료]\n");
				} else {
					System.out.println("[추가실패]\n");
				}
				break;
			} else if (select == 2) {
				return;
			} else {
				System.out.println("<잘못된 접근입니다!>\n");
				continue;
			}
		}

	}
}
