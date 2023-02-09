package com.lancesoft.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.entity.Inventory;
import com.lancesoft.service.AdminDashboardService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin")

public class InventoryController {

	@Autowired
	AdminDashboardService adminDashboardServiceImpl;


	@Bulkhead(name="default")
	@PostMapping("/addInventory")
	public ResponseEntity<?> addInventory(@RequestBody Inventory inventory) {
		//System.err.println("add inv ms called");
		return adminDashboardServiceImpl.addInventory(inventory);
	}

	@Bulkhead(name="default")
	@GetMapping("/getAllInventory")
	public ResponseEntity<?> getAllInventories() {
		return adminDashboardServiceImpl.getAllInventories();
	}

	@Bulkhead(name="default")
	@GetMapping("/deleteinventory")
	public ResponseEntity<?> deleteInventory(@RequestBody Inventory inventory) {
		return adminDashboardServiceImpl.deleteInventory(inventory);
	}

	@Bulkhead(name="default")
	@PostMapping("/updateinventory")
	public ResponseEntity<?> updateInventory(@RequestBody Inventory inventory) {
		return adminDashboardServiceImpl.addInventory(inventory);
	}

	@GetMapping("/findInventoryByProductName/{prodName}")
	public Inventory findByProductName(@PathVariable String prodName) {
		 return adminDashboardServiceImpl.findByProductName(prodName);
	}
	

	@PostMapping("/saveAllInventories")
	public void saveAllInventories(@RequestBody List<Inventory> addinvenInventories) {
		adminDashboardServiceImpl.saveAllInventories(addinvenInventories);
	}

}
