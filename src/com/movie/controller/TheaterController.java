package com.movie.controller;

import java.util.List;
import java.util.Scanner;

import com.movie.dto.MovieDTO;
import com.movie.dto.TheaterDTO;
import com.movie.service.TheaterService;
import com.movie.view.TheaterView;

public class TheaterController {

	static TheaterService theaterService = new TheaterService();

	public static int selectTheaterMenu(Scanner sc, MovieDTO movie) {
		int result = -1;

		while (result == -1) {
			int select = showTheaters(sc); // 상영관 조회 옵션 선택 메뉴

			switch (select) {
			case 1 -> {
				List<TheaterDTO> theaterList = theaterService.showAll(movie);
				while (true) {
					TheaterView.showAll(theaterList, movie.getMovieTitle());
					int subResult = subMenu(sc, theaterList); // 1이면 홈으로 0이면 이전으로 -1이면 머무르기
					if (subResult == 1) {
						result = 1;
						break;
					} 
					else if(subResult == 0) break;
					else continue;
				}
			}
			case 2 -> {

			}
			case 3 -> {
				result = 0;
			}
			}
			if(result == 0 || result == 1) break;
		}

		return result;
	}

	private static int showTheaters(Scanner sc) {
		System.out.println("------------------------------------");
		System.out.println("1.전체상영관조회 | 2.날짜로조회 | 3.뒤로가기(영화선택메뉴)");
		System.out.println("------------------------------------");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());

		return select;
	}

	private static int subMenu(Scanner sc, List<TheaterDTO> theaterList) {
		System.out.println("------------------------------------");
		System.out.println("1.상영관선택 | 2.뒤로가기(상영관메뉴)");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());
		int result;

		if (select == 1) {
			int theaterNum;
			while (true) {
				System.out.print("상영관번호 입력>> ");
				theaterNum = Integer.parseInt(sc.nextLine());
				if (theaterNum <= 0 || theaterNum > theaterList.size()) {
					System.out.println("<잘못된 접근입니다!>");
					continue;
				} else {
					break;
				}
			}
			result = SeatController.selectSeatMenu(sc, theaterList.get(theaterNum - 1)) == 0 ? -1 : 1;
		} else if (select == 2) { // 뒤로가기(영화리스트 조회)
			result = 0;
		} else {
			System.out.println("<잘못된 접근입니다!>");
			result = -1;
		}
		return result;

	}

}
