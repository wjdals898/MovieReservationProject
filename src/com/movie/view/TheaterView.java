package com.movie.view;

import java.util.List;

import com.movie.dto.TheaterDTO;

public class TheaterView {

	public static void showAll(List<TheaterDTO> theaterList, String title) {
		print(title);
		int i = 1;
		for(TheaterDTO theater:theaterList) {
			String seatsCount = theater.getRemainingSeatsCount() <= 0? "(매진)" : theater.getRemainingSeatsCount()+"석";
			System.out.printf("<%d>\t%s\t%s\t%s\n",
					i++, theater.getScreeningDate(), theater.getScreeningTime(), seatsCount);
		}
	}
	public static void print(String title) {
		System.out.println("-----------------------------------------");
		System.out.println("["+title+"] 상영관 리스트");
		System.out.println("-----------------------------------------");
		System.out.println(" 번호\t상영날짜\t\t상영시간\t잔여좌석수");
		System.out.println("-----------------------------------------");
	}

}
