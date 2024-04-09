package com.movie.service;

import java.util.List;

import com.movie.dto.TheaterDTO;
import com.movie.model.SeatDAO;
import com.movie.model.SeatDTO;

public class SeatService {

	SeatDAO seatDao = new SeatDAO();
	
	public List<SeatDTO> showAll(int theaterId) {
		return seatDao.showAll(theaterId);
	}

}
