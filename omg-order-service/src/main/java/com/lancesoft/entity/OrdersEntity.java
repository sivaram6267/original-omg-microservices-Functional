package com.lancesoft.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Component
public class OrdersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sql")
	@GenericGenerator(name = "user_sql", strategy = "com.lancesoft.entity.CustomeIdGenerator", parameters = {
			@Parameter(name = CustomeIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = CustomeIdGenerator.VALUE_PREFIX_PARAMAETER, value = "ORD"),
			@Parameter(name = CustomeIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d") })

	private String orderId;
	private String userName;
	
	
	@Transient
	@OneToOne(cascade = CascadeType.ALL)
	AddressEntity addressEntity;
		
	String addressId;
	
	String deliveryDate;
	
	String paymentMode;
	
	String amount;
	
	String paymentStatus;
	
	String deliveryStatus;
	
	@Transient
	@OneToOne
	DeliveryAgentEntity agentEntity;
	
}
