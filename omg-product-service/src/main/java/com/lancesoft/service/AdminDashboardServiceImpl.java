package com.lancesoft.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.RegistrationCustomException;
import com.lancesoft.dao.CategoryRepo;
import com.lancesoft.dao.ProductsRepo;
import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
	@Autowired
	CategoryRepo repo;



	

	@Autowired
	ProductsRepo productsRepo;

	@Autowired
	CategoryRepo categoryRepo;




	//Logger logger = Logger.getLogger(AdminDashboardServiceImpl.class);

	public void addCategory(CategoriesEntity categoriesEntity) {
		if (categoriesEntity == null) {
			throw new RegistrationCustomException("");
		} else {
			repo.save(categoriesEntity);
		}
	}



	public ResponseEntity updateProduct(HttpServletRequest httpServletRequest, ProductsEntity productsEntity) {

		ProductsEntity productsEntityFromRepo = productsRepo.findByProdName(productsEntity.getProdName());
		productsEntity.setProdId(productsEntityFromRepo.getProdId());
		productsRepo.save(productsEntity);
		return new ResponseEntity(productsEntity, HttpStatus.OK);

	}

	public ResponseEntity getAllCategory() {
		return new ResponseEntity(categoryRepo.findAll(), HttpStatus.OK);
	}

	public ResponseEntity getAllProducts(String userName) {

//		if (userName != null) {
//			
//			System.err.println("products with token");
//
//			List<ProductsEntity> productsEntities = productsRepo.findAll();
//			Set<ProductsEntity> productsEntities2 = new HashSet<>();
//			List<MyCart> myCart = cartRepo.findByUserId(userName);
//			for (MyCart myCart2 : myCart) {
//
//				for (ProductsEntity productsEntity2 : productsEntities) {
//					if (myCart2.getProductsEntity().equals(productsEntity2)) {
//						productsEntity2.setItemCount(myCart2.getItemCount());
//						productsEntities2.add(productsEntity2);
//					}
//					productsEntities2.add(productsEntity2);
//				}
//
//			}
//			return new ResponseEntity<>(productsEntities2, HttpStatus.OK);
//		} else {
			return new ResponseEntity<>(productsRepo.findAll(), HttpStatus.OK);
		//}
	}

	@Override
	public ResponseEntity<?> getoneCategory(String catName) {
		CategoriesEntity categoriesEntity = categoryRepo.findByCatName(catName);
		List<ProductsEntity> entity = productsRepo.findByCategoriesEntity(categoriesEntity);
		return new ResponseEntity(entity, HttpStatus.OK);
	}

	

	}

	

