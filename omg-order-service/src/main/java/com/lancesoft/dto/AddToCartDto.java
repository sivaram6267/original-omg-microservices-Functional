package com.lancesoft.dto;

import lombok.Data;

@Data
public class AddToCartDto {

	private String prodId;
	private Double qty;
	private String unit;
//	private int item;
}
