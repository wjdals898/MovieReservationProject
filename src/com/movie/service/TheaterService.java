package com.movie.service;

import java.sql.Date;
import java.util.List;

import com.movie.dto.MovieDTO;
import com.movie.dto.TheaterDTO;
import com.movie.model.TheaterDAO;

public class TheaterService {

	TheaterDAO theaterDao = new TheaterDAO();

	public List<TheaterDTO> showAll(MovieDTO movie) {
		return theaterDao.showAll(movie);
	}

	public List<TheaterDTO> showByDate(MovieDTO movie, Date selectDate) {
		return theaterDao.showByDate(movie, selectDate);
	}

	public int addTheater(TheaterDTO newTheater) {
		return theaterDao.addTheater(newTheater);
	}
	
	
}
