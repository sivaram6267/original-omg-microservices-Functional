package com.lancesoft.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lancesoft.entity.ProductsEntity;

@FeignClient(name  = "OMG-PRODUCT-SERVICE", path = "/api/admin")
public interface ProductsProxy {
	
	@GetMapping("/findProdById/{prodId}")
	public ProductsEntity getProdById(@PathVariable String prodId);
	
	@GetMapping("/findByProdName/{prodName}")
	public ProductsEntity findByProdName(@PathVariable String prodName);

}
