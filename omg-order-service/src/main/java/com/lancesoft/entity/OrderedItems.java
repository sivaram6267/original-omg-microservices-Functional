package com.lancesoft.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class OrderedItems {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String itemName;
	private Double itemCost;
	private Double quantity;
	private Double Price;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	OrdersEntity ordersEntity;

	
	@Transient
	@ManyToOne
	@JoinColumn(name = "prod_id")
	ProductsEntity productsEntity;
	
	String productId;
}
