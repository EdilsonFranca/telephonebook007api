package com.telephonebook.dto;

import lombok.Data;

@Data
public class JwtDTO {
	private String name;
	private String email;
	private String token;
	private String type = "Bearer";

	public JwtDTO(String accessToken, String name, String email) {
		this.name = name;
		this.email = email;
		this.token = accessToken;
	}
}