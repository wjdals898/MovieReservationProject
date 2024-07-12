package com.movie.dto;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
public class ReservationDTO {
	private int id;
	private String movieTitle;
	private String runningTime;
	private Date screeningDate;
	private String screeningTime;
	private String seats;
	private String reservationDate;
	
}
