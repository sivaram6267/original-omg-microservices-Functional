package com.lancesoft.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.kafka.KafkaConstants;

@FeignClient(name  = "OMG-PRODUCT-SERVICE", path = "/api/admin")

public interface ProductsProxy {
	
	@GetMapping("/findProdById/{prodId}")
	public ProductsEntity getProdById(@PathVariable String prodId);

	@GetMapping("/saveProduct")
	public void saveProduct(@RequestBody ProductsEntity productsEntity);
	
	
	@GetMapping("/findByProdName/{prodName}")
	public ProductsEntity findByProdName(@PathVariable String prodName);

}
