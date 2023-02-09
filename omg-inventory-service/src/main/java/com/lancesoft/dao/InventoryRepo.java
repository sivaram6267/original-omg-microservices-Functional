package com.lancesoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, String> {

	Inventory findByProductName(String productName);

	boolean existsByProductName(String productName);

}
