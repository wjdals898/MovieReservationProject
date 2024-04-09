package com.movie.service;

import java.util.List;

import com.movie.dto.MovieDTO;
import com.movie.dto.TheaterDTO;
import com.movie.model.TheaterDAO;

public class TheaterService {

	TheaterDAO theaterDao = new TheaterDAO();

	public List<TheaterDTO> showAll(MovieDTO movie) {
		return theaterDao.showAll(movie);
	}
	
	
}
