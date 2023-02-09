package com.lancesoft.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.RegistrationCustomException;
import com.lancesoft.dao.InventoryRepo;
import com.lancesoft.entity.Inventory;
import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.feign.ProductsProxy;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

	
	@Autowired
	ProductsProxy  productsProxy;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	InventoryRepo inventoryRepo;

	Logger logger = Logger.getLogger(AdminDashboardServiceImpl.class);



	@Override
	public ResponseEntity<Inventory> addInventory(Inventory inventory) {
		System.err.println("add inventory called");
		logger.info("addInventory method started...");
		if (inventoryRepo.existsByProductName(inventory.getProductName())) {
			throw new RegistrationCustomException("invalid data");
		} else {
			ProductsEntity productsEntity =productsProxy.findByProdName(inventory.getProductName());			
			inventory.setCategoriesEntity(productsEntity.getCategoriesEntity());
			return new ResponseEntity<Inventory>(inventoryRepo.save(inventory), HttpStatus.ACCEPTED);
		}
	}

	@Override
	public ResponseEntity<List<?>> getAllInventories() {
		return new ResponseEntity<List<?>>(inventoryRepo.findAll(), HttpStatus.ACCEPTED);

	}

	@Override
	public ResponseEntity<?> deleteInventory(Inventory inventory) {
		Inventory inventorydelete = inventoryRepo.findByProductName(inventory.getProductName());
		inventoryRepo.delete(inventorydelete);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@Override
	public Inventory findByProductName(String prodName) {
		return inventoryRepo.findByProductName(prodName);
	}

	@Override
	public void saveAllInventories(List<Inventory> addinvenInventories) {
		inventoryRepo.saveAll(addinvenInventories);	
	}
	
	
}
