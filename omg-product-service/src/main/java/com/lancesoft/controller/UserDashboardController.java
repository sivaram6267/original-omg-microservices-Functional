package com.lancesoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.service.UserDashboardService;

@RestController
@RequestMapping("/api/user")
public class UserDashboardController {

	@Autowired
	UserDashboardService userDashboardService;

	@GetMapping("/searchProduct")
	public List<ProductsEntity> searchproduct(@RequestParam("query") String query) {
		return userDashboardService.searchProduct(query);
	}

	@GetMapping("/getonecategory")
	public ResponseEntity<?> getoneCategory(@RequestParam("catName") String catName) {
		return userDashboardService.getoneCategory(catName);
	}

	@GetMapping("/getOneProduct")
	public ResponseEntity<?> getOneProduct(@RequestParam("pName") String pName) {
		return userDashboardService.getOneProduct(pName);
	}

}
