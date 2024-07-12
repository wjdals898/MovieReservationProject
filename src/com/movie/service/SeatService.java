package com.movie.service;

import java.util.ArrayList;
import java.util.List;

import com.movie.dto.SeatDTO;
import com.movie.dto.TheaterDTO;
import com.movie.model.SeatDAO;

public class SeatService {

	SeatDAO seatDao = new SeatDAO();
	
	public List<SeatDTO> showAll(int theaterId) {
		return seatDao.showAll(theaterId);
	}

	public List<SeatDTO> showSeatsInfo(String[] seatNumList, int theaterId) {
		return seatDao.showBySeatNum(seatNumList, theaterId);
	}

}
