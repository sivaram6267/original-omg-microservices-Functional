package com.lancesoft.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.dto.ProductsDto;
import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;
import com.lancesoft.service.AdminDashboardService;
import com.lancesoft.service.ProductsService;

@RestController
@RequestMapping("/api/admin")
public class AdminProductsController {

	@Autowired
	AdminDashboardService adminDashboardServiceImpl;

	@Autowired
	ProductsService productsServiceImpl;

	@PostMapping("/addCategory")
	public ResponseEntity<?> addCategory(@RequestBody CategoriesEntity categoriesEntity) {
		adminDashboardServiceImpl.addCategory(categoriesEntity);
		return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping("/addProducts")
	public ResponseEntity<?> addProducts(@RequestBody ProductsDto productsDto) {
		return productsServiceImpl.addProducts(productsDto);
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<?> getAllProducts(String userName) {
		// String userName =null;
		try {
			// userName= authentication.getName();
		} catch (Exception e) {

		}
		return adminDashboardServiceImpl.getAllProducts(userName);
	}

	@PutMapping("/updateProd")
	public ResponseEntity<?> updatedProduct(HttpServletRequest httpServletRequest,
			@RequestBody ProductsEntity productsEntity) {
		try {
			return adminDashboardServiceImpl.updateProduct(httpServletRequest, productsEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("exception occured", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findProdById/{prodId}")
	public ProductsEntity findByProdId(@PathVariable String prodId) {
		return productsServiceImpl.findByProdId(prodId);
	}

	@GetMapping("/findByProdName/{prodName}")
	public ProductsEntity findByProdName(@PathVariable String prodName) {
		return productsServiceImpl.findByProdName(prodName);

	}
	
	@GetMapping("/findByProdName")
	public ProductsEntity findByProductName(@RequestParam("prodName") String prodName) {
		return productsServiceImpl.findByProdName(prodName);
	}
	
	@GetMapping("/getCategories")
	public ResponseEntity<?> getAllCategories() {
		return adminDashboardServiceImpl.getAllCategory();
	}
	
	@GetMapping("/getProdByProdName")
	public ProductsEntity getProdByProdName(@RequestParam("prodName") String prodName) {
		return productsServiceImpl.findByProdName(prodName);
	}

}
