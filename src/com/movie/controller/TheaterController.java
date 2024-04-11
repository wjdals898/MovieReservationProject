package com.movie.controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.movie.MainApplication;
import com.movie.dto.MovieDTO;
import com.movie.dto.TheaterDTO;
import com.movie.service.TheaterService;
import com.movie.util.DateUtil;
import com.movie.view.TheaterView;

public class TheaterController {

	static TheaterService theaterService = new TheaterService();

	public static int selectTheaterMenu(Scanner sc, MovieDTO movie) {
		int result = -1;

		while (result == -1) {
			int select = showTheaters(sc); // 상영관 조회 옵션 선택 메뉴
			List<TheaterDTO> theaterList = null;
			switch (select) {
			case 1 -> {
				theaterList = theaterService.showAll(movie);
				while (true) {
					if(theaterList.size()<=0) {
						TheaterView.print(movie.getMovieTitle());
						System.out.println("              상영관이 없습니다.\n");
						result = 0;
						break;
					}
					TheaterView.showAll(theaterList, movie.getMovieTitle());
					int subResult = subMenu(sc, theaterList); // 1이면 홈으로 0이면 이전으로 -1이면 머무르기
					if (subResult == 1) {
						result = 1;
						break;
					} else if (subResult == 0)
						break;
					else
						continue;
				}
			}
			case 2 -> {
				Date selectDate = selectDateMenu(sc); // 조회할 날짜 입력 메뉴
				theaterList = theaterService.showByDate(movie, selectDate);
				while (true) {
					if(theaterList.size()<=0) {
						TheaterView.print(movie.getMovieTitle());
						System.out.println("              상영관이 없습니다.\n");
						result = 0;
						break;
					}
					TheaterView.showAll(theaterList, movie.getMovieTitle());
					int subResult = subMenu(sc, theaterList); // 1이면 홈으로 0이면 이전으로 -1이면 머무르기
					if (subResult == 1) {
						result = 1;
						break;
					} else if (subResult == 0)
						break;
					else
						continue;
				}
			}
			case 3 -> {
				result = 0;
			}
			}
			if (result == 0 || result == 1)
				break;
		}

		return result;
	}

	private static int showTheaters(Scanner sc) {
		System.out.println("-----------------------------------------");
		System.out.println("1.전체상영관조회 | 2.날짜로조회 | 3.뒤로가기(영화선택메뉴)");
		System.out.println("-----------------------------------------");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());

		return select;
	}

	private static Date selectDateMenu(Scanner sc) {
		Date resultDate = null;
		while (true) {
			System.out.print("조회할 날짜를 입력하세요>> ");
			String selectDate = sc.nextLine();
			System.out.println();
			
			Date now = DateUtil.getSQLDate((new Date(System.currentTimeMillis())).toString());
			resultDate = DateUtil.getSQLDate(selectDate);
			
			if(resultDate.before(now)) {	// 현재시간 이전 날짜일 경우
				System.out.println("<이전 날짜는 조회할 수 없습니다.>\n");
			} else {
				break;
			}
		}
		return resultDate;
	}

	private static int subMenu(Scanner sc, List<TheaterDTO> theaterList) {
		System.out.println("-----------------------------------------");
		System.out.println("1.상영관선택 | 2.뒤로가기(상영관메뉴)");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());
		int result;

		if (select == 1) {
			if (MainApplication.user.getId() == 0) {
				System.out.println("<로그인 후 이용가능합니다!>\n");
				return -1;
			}
			int theaterNum;
			while (true) {
				System.out.print("상영관번호 입력>> ");
				theaterNum = Integer.parseInt(sc.nextLine());
				System.out.println();
				if (theaterNum <= 0 || theaterNum > theaterList.size()) {
					System.out.println("<잘못된 접근입니다!>\n");
					continue;
				} else {
					break;
				}
			}
			result = SeatController.selectSeatMenu(sc, theaterList.get(theaterNum - 1)) == 0 ? -1 : 1;
		} else if (select == 2) { // 뒤로가기(영화리스트 조회)
			result = 0;
		} else {
			System.out.println("<잘못된 접근입니다!>\n");
			result = -1;
		}
		return result;

	}

	public static int addTheaterMenu(Scanner sc, MovieDTO movie) {
		System.out.println("-----------------------------------------");
		System.out.println("추가할 상영관 정보를 입력하세요");
		System.out.println(" 영화제목 |"+movie.getMovieTitle());
		System.out.print(" 상영날짜 | ");
		Date screeningDate = DateUtil.getSQLDate(sc.nextLine());
		System.out.print(" 상영시간 | ");
		String screeningTime = sc.nextLine();
		System.out.print(" 좌석갯수 | ");
		int seats = Integer.parseInt(sc.nextLine());
		System.out.println("-----------------------------------------");
		int menuResult = -1;
		
		while (menuResult == -1) {
			System.out.println("1.추가하기 | 2.뒤로가기");
			System.out.print("작업을 선택하세요>> ");
			int select = Integer.parseInt(sc.nextLine());
			if (select == 1) {
				TheaterDTO newTheater = new TheaterDTO();
				newTheater.setMovie(movie);
				newTheater.setScreeningDate(screeningDate);
				newTheater.setScreeningTime(screeningTime);
				newTheater.setRemainingSeatsCount(seats);
				
				int result = theaterService.addTheater(newTheater); // 1이면 추가성공 0이면 실패
				if(result == 1) {
					System.out.println("[추가완료]\n");
					menuResult = 1;
				} else {
					System.out.println("[추가실패]\n");
					menuResult = 0;
				}
			} else if (select == 2) {
				menuResult = 0;
			} else {
				System.out.println("<잘못된 접근입니다!>\n");
				continue;
			}
		}
		return menuResult;
	}

}
