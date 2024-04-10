package com.movie.controller;

import java.util.List;
import java.util.Scanner;

import com.movie.dto.SeatDTO;
import com.movie.dto.TheaterDTO;
import com.movie.service.ReservationService;
import com.movie.view.ReservationView;

public class ReservationController {
	
	static ReservationService reservationService = new ReservationService();

	public static int reservationMenu(Scanner sc, TheaterDTO theater, List<SeatDTO> seatInfo) {
		int result = -1;
		ReservationView.showBeforeReservation(theater, seatInfo);
		
		while(true) {
			System.out.println("-----------------------------------------");
			System.out.println("1.예매하기 | 2.뒤로가기(좌석선택메뉴)");
			System.out.print("서비스를 선택하세요>> ");
			int select = Integer.parseInt(sc.nextLine());
			if(select == 1) {
				System.out.println("theater_id = "+theater.getId());
				List<Integer> resultList = reservationService.createReservation(theater.getId(), seatInfo);	// 좌석별 예매결과 boolean 리스트로 반환
				if(resultList.size()<=0 || resultList.size()!=seatInfo.size()) {
					System.out.println("<예매실패> 예매할 수 없는 좌석입니다.");
					result = -1;
				} else {
					System.out.println("*****************[예매완료]*****************");
					result = 1;	// 홈으로
					break;
				}
			} else if(select == 2) {
				result = 0;	// 좌석 선택메뉴로
				break;
			} else {
				System.out.println("<잘못된 접근입니다!>");
				continue;
			}
		}
		
		return result;
	}

}
