package com.lancesoft.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.OrderedItems;
import com.lancesoft.entity.OrdersEntity;

public interface OrderedItemsRepo extends JpaRepository<OrderedItems, Integer> {

	List<OrderedItems> findByOrdersEntity(OrdersEntity ordersEntityList);


}
