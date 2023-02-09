package com.lancesoft.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageSectionEntity {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	private int id;
	
//	@OneToOne
//	private RegistrationEntity registrationEntity;
//	
//	@OneToMany(fetch = FetchType.EAGER)
//	private List<OrdersEntity> ordersEntities;
	
		
	private Date deliveryDate;
	
	private String packagingStatus;
}
