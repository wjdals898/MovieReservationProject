package com.movie.view;

import java.util.List;

import com.movie.dto.SeatDTO;

public class SeatView {

	public static void showSeats(List<SeatDTO> seatList) {
		System.out.println("-----------------------------------------");
		System.out.println("좌석조회");
		System.out.println("-----------------------------------------");
		for(int i=0;i<seatList.size()/5;i++) {
			for(int j=0;j<5;j++) {
				SeatDTO seat = seatList.get(i*5+j);
				if(seat.isSoldout()) {
					System.out.print("[  ]\t");
				} else {
					System.out.print("["+seat.getSeatNum()+"]\t");
				}
			}
			System.out.println();
		}
	}

	
}
