package com.lancesoft.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
public class DeliveryAgentEntity {
	
	@Id
    private int id;
	
}
