package com.lancesoft.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminRegistrationDto {

	
	private Integer id;
	private String firstName;
	private String lastName;
	
	private String email;
	private String dateOfBirth;

	private String phoneNumber;


	private String userName;
	private String password;

}
