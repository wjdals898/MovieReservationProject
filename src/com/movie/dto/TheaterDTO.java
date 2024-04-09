package com.movie.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
public class TheaterDTO {
	private int id;
	private MovieDTO movie;
	private Date screeningDate;
	private String screeningTime;
	private int remainingSeatsCount;
}
