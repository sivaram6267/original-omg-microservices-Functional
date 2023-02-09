package com.lancesoft.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
public class DeliveryDepartmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty
	@OneToOne
	private OrderedItems orderedItems;
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	@OneToMany(fetch = FetchType.EAGER)
	private List<ProductsEntity> productsEntity;
	
	private int totalOrders;
	
	private int readyForDispatch;
	
	private int pendingOrders;
	
	private int successfulOrders;
	
	private int ordersReturned;
}
