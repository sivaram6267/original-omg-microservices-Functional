package com.lancesoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.CustomException;
import com.lancesoft.dao.CategoryRepo;
import com.lancesoft.dao.ProductsRepo;
import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;

@Service
public class UserDashboardServiceImpl implements UserDashboardService {

	@Autowired
	private ProductsRepo productsRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	

	/*
	 * @Autowired private MyCartRepo myCartRepo;
	 * 
	 * @Autowired private PaytmDetailPojo paytmDetailPojo;
	 * 
	 * @Autowired private Environment env;
	 */



	

	
	// searching the products
	@Override
	public List<ProductsEntity> searchProduct(String query) {
		try {
			List<ProductsEntity> products = productsRepo.searchProducts(query);
			return products;
		} catch (Exception e) {
			throw new CustomException("error occured", e.getLocalizedMessage());
		}
	}

	@Override
	public ResponseEntity<?> getoneCategory(String catName) {
		try {
			CategoriesEntity categoriesEntity = categoryRepo.findByCatName(catName);
			List<ProductsEntity> entity = productsRepo.findByCategoriesEntity(categoriesEntity);
			return new ResponseEntity<Object>(entity, HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException("error occured", e.getLocalizedMessage());
		}
	}

	@Override
	public ResponseEntity<?> getOneProduct(String pName) {

		return new ResponseEntity<>(productsRepo.findByProdName(pName),HttpStatus.OK);
	}

}