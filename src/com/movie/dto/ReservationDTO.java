package com.movie.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
public class ReservationDTO {
	private int id;
	private String nickname;
	private String movie_title;
	private Date screening_date;
	private String screening_time;
	private String[] seats;
	private Date reservation_date;
}
