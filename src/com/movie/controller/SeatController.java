package com.movie.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.movie.dto.SeatDTO;
import com.movie.dto.TheaterDTO;
import com.movie.service.SeatService;
import com.movie.view.SeatView;

public class SeatController {

	static SeatService seatService = new SeatService();

	public static int selectSeatMenu(Scanner sc, TheaterDTO theaterDTO) {
		int result = -1;
		List<SeatDTO> seatList = seatService.showAll(theaterDTO.getId());
		if (seatList.size() <= 0) {
			System.out.println("<선택한 상영관은 *매진*입니다. 다른 상영관을 선택하세요.>\n");
			return 0; // 상영관 리스트 조회
		}
		while (true) {
			SeatView.showSeats(seatList);
			result = seatSubMenu(sc, theaterDTO);	// 1이면 홈으로 0이면 이전으로 -1이면 머무르기
			if(result==1 || result == 0) {
				break;
			} else continue;
		}
		return result;	// 1이면 예매완료(홈으로) 0이면 이전으로(영화조회메뉴)
	}

	private static int seatSubMenu(Scanner sc, TheaterDTO theater) {
		System.out.println("-----------------------------------------");
		System.out.println("1.좌석선택 | 2.뒤로가기(상영관선택메뉴)");
		System.out.print("서비스를 선택하세요>> ");
		int select = Integer.parseInt(sc.nextLine());
		List<SeatDTO> seatInfo = null;
		int result = -1;
		
		if(select == 1) {
			while(true) {
				System.out.print("예매할 좌석번호를 모두 선택하세요>> ");
				String seatNum = sc.nextLine();
				String[] seatNumList = seatNum.split(" ");
				seatInfo = seatService.showSeatsInfo(seatNumList, theater.getId());	// 좌석정보 가져오기
				//seatInfo.stream().forEach((seat)->System.out.println(seat));
				System.out.println("선택한 좌석 수 = "+seatNumList.length);
				if(seatInfo.size() != seatNumList.length) {
					System.out.println("예매할 수 없는 좌석번호가 포함되어있습니다. 다시 선택하세요.>>");
					continue;
				} else {
					break;
				}
			}
			result = ReservationController.reservationMenu(sc, theater, seatInfo)==0?-1:1;	// 1이면 예매완료 0이면 머무르기
		} else if(select == 2) {
			result = 0;
		} else {
			System.out.println("<잘못된 접근입니다!>\n");
			result = -1;
		}
		
		return result;
	}

}
