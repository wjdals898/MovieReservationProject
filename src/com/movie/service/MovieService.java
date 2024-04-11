package com.movie.service;

import java.util.List;

import com.movie.dto.MovieDTO;
import com.movie.model.MovieDAO;

public class MovieService {

	MovieDAO movieDao = new MovieDAO();
	
	public List<MovieDTO> showAll() {
		return movieDao.showAll();
	}

	public List<MovieDTO> showByScreening() {
		return movieDao.showByScreening();
	}

	public List<MovieDTO> searchByTitle(String title) {
		return movieDao.searchByTitle(title);
	}

	public int addMovie(MovieDTO newMovie) {
		return movieDao.addMovie(newMovie);
	}

}
