package com.lancesoft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

//@Entity
@Data
public class DeliveryLocations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String country;
	
	private String locationName;
	
	private String zone;
	
	private String pinCode;
	
	
}
