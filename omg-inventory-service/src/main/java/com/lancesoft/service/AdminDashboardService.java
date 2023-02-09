package com.lancesoft.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lancesoft.entity.Inventory;

public interface AdminDashboardService  {


public ResponseEntity<?> addInventory(Inventory inventory);
ResponseEntity<?> getAllInventories();
public ResponseEntity<?> deleteInventory(Inventory inventory);
public Inventory findByProductName(String prodName);
public void saveAllInventories(List<Inventory> addinvenInventories);

}
