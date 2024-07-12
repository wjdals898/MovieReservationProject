package com.movie.service;

import java.util.List;

import com.movie.dto.ReservationDTO;
import com.movie.dto.SeatDTO;
import com.movie.model.ReservationDAO;

public class ReservationService {

	ReservationDAO reservationDao = new ReservationDAO();
	
	public List<Integer> createReservation(int theaterId, List<SeatDTO> seatInfo) {
		return reservationDao.createReservation(theaterId, seatInfo);
	}

	public List<ReservationDTO> showAll() {
		return reservationDao.showAll();
	}

	public int cancelReservation(ReservationDTO cancelReservation) {
		return reservationDao.cancelReservation(cancelReservation);
	}

}
