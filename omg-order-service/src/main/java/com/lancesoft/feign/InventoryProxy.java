package com.lancesoft.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.lancesoft.entity.Inventory;

@FeignClient(name  = "OMG-INVENTORY-SERVICE", path ="/api/admin")
public interface InventoryProxy {

	@GetMapping("/findInventoryByProductName/{prodName}")
	public Inventory findByProductName(@PathVariable String prodName);

	@PostMapping("/saveAllInventories")
	public void saveAllInventories(@RequestBody List<Inventory> addinvenInventories);

}
