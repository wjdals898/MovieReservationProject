package com.movie.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
public class SeatDTO {
	private int id;
	private String seatNum;
	private boolean isSoldout;
}
