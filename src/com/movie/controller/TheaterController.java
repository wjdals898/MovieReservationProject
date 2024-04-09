package com.movie.controller;

import java.util.List;
import java.util.Scanner;

import com.movie.dto.MovieDTO;
import com.movie.dto.TheaterDTO;
import com.movie.service.TheaterService;
import com.movie.view.TheaterView;

public class TheaterController {

	static TheaterService theaterService = new TheaterService();

	public static void selectTheaterMenu(Scanner sc, MovieDTO movie) {
		int select = showTheaters(sc); // 상영관 조회 옵션 선택 메뉴

		switch (select) {
		case 1 -> {
			List<TheaterDTO> theaterList = theaterService.showAll(movie);
			TheaterView.showAll(theaterList, movie.getMovieTitle());
			subMenu(sc, theaterList);
		}
		case 2 -> {

		}
		case 3 -> {
			return;
		}
		}
	}

	private static int showTheaters(Scanner sc) {
		System.out.println("------------------------------------");
		System.out.println("1.전체상영관조회 | 2.날짜로조회 | 3.뒤로가기");
		System.out.println("------------------------------------");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());

		return select;
	}

	private static void subMenu(Scanner sc, List<TheaterDTO> theaterList) {
		while (true) {
			System.out.println("------------------------------------");
			System.out.println("1.상영관선택 | 2.뒤로가기");
			System.out.print("서비스를 선택하세요>> ");
			int select = Integer.parseInt(sc.nextLine());
			
			if(select == 1) {
				System.out.print("상영관번호 입력>> ");
				int theaterNum = Integer.parseInt(sc.nextLine());
				if (theaterNum <= 0 || theaterNum > theaterList.size()) {
					System.out.println("<잘못된 접근입니다!>");
					continue;
				}
				SeatController.selectSeatMenu(sc, theaterList.get(theaterNum - 1));
			} else if(select == 2) {
				return;
			} else {
				System.out.println("<잘못된 접근입니다!>");
				continue;
			}
		}

	}

}
