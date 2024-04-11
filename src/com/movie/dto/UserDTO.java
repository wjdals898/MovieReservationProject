package com.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
@NoArgsConstructor
public class UserDTO {
	private int id;
	private String username;
	private String nickname;
	private boolean isManager;
	
	public UserDTO(String nickname) {
		this.nickname = nickname;
	}
}
