package com.lancesoft.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lancesoft.entity.ProductsEntity;

public interface UserDashboardService {

	List<ProductsEntity> searchProduct(String query);

	ResponseEntity<?> getoneCategory(String catName);

	ResponseEntity<?> getOneProduct(String pName);

}
