package com.movie.controller;

import java.util.List;
import java.util.Scanner;

import com.movie.dto.TheaterDTO;
import com.movie.model.SeatDTO;
import com.movie.service.SeatService;

public class SeatController {
	
	static SeatService seatService = new SeatService();

	public static void selectSeatMenu(Scanner sc, TheaterDTO theaterDTO) {
		List<SeatDTO> seatList = seatService.showAll(theaterDTO.getId());
		int i = 1;
		for(SeatDTO seat:seatList) {
			System.out.println("<"+i++ +"> "+seat.getSeatNum());
		}
	}

}
