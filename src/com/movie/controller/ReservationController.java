package com.movie.controller;

import java.util.List;
import java.util.Scanner;

import com.movie.dto.ReservationDTO;
import com.movie.dto.SeatDTO;
import com.movie.dto.TheaterDTO;
import com.movie.service.ReservationService;
import com.movie.view.ReservationView;

public class ReservationController {

	static ReservationService reservationService = new ReservationService();

	public static int reservationMenu(Scanner sc, TheaterDTO theater, List<SeatDTO> seatInfo) {
		int result = -1;
		ReservationView.showBeforeReservation(theater, seatInfo);

		while (true) {
			System.out.println("-----------------------------------------");
			System.out.println("1.예매하기 | 2.뒤로가기(좌석선택메뉴)");
			System.out.print("서비스를 선택하세요>> ");
			int select = Integer.parseInt(sc.nextLine());
			if (select == 1) {
				List<Integer> resultList = reservationService.createReservation(theater.getId(), seatInfo); // 좌석별 예매결과
																											// boolean
																											// 리스트로 반환
				if (resultList.size() <= 0 || resultList.size() != seatInfo.size()) {
					System.out.println("<예매실패> 예매할 수 없는 좌석입니다.\n");
					result = -1;
				} else {
					System.out.println("*****************[예매완료]*****************\n");
					result = 1; // 홈으로
					break;
				}
			} else if (select == 2) {
				result = 0; // 좌석 선택메뉴로
				break;
			} else {
				System.out.println("<잘못된 접근입니다!>\n");
				continue;
			}
		}

		return result;
	}

	public static int showReservationMenu(Scanner sc) { // 메인메뉴-예매내역 조회메뉴
		int result = -1;

		while (result == -1) {
			List<ReservationDTO> reservationList = reservationService.showAll();
			ReservationView.showReservationList(reservationList);
			
			System.out.println("-----------------------------------------");
			System.out.println("1.예매취소 | 2.뒤로가기(메인으로)");
			System.out.print("서비스를 선택하세요>> ");
			int select = Integer.parseInt(sc.nextLine());
			if (select == 1) {
				ReservationView.showCancelableList(reservationList);
				while(true) {
					System.out.print("취소할 예매번호를 선택하세요>> ");
					int reservation_id = Integer.parseInt(sc.nextLine());
					ReservationDTO cancelReservation = reservationList.stream()
						.filter((reservation)->reservation.getId()==reservation_id)
						.findAny().orElse(null);
					if (cancelReservation == null) {
						System.out.println("잘못된 예매번호입니다.\n");
						break;
					} else {
						int cancelResult = reservationService.cancelReservation(cancelReservation);	// 1이면 취소완료 0이면 실패
						System.out.println(cancelResult==1?"<취소완료 되었습니다.>\n":"<예매취소에 실패하였습니다.>\n");
						break;
					}
				}
			} else if (select == 2) {
				result = 0;
			} else {
				System.out.println("<잘못된 접근입니다!>\n");
			}
		}

		return result;
	}

}
