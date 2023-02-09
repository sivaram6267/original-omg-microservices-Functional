package com.lancesoft.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.RegistrationCustomException;
import com.lancesoft.dao.CategoryRepo;
import com.lancesoft.dao.ProductsRepo;
import com.lancesoft.dto.ProductsDto;
import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	ProductsRepo productsRepo;

	@Autowired
	CategoryRepo categoryRepo;

//	@Autowired
//	KafkaTemplate<String, ProductsEntity> kafkaTemplate ;

	// Logger logger = Logger.getLogger(ProductsServiceImpl.class);

	public ResponseEntity<?> addProducts(ProductsDto productsDto) {
		ProductsEntity productsEntity = new ProductsEntity();
		ModelMapper mapper = new ModelMapper();
		//////
		if (productsDto == null) {
			System.out.println("product is null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else if (productsRepo.existsByProdName(productsDto.getProdName())) {
			throw new RegistrationCustomException("product already exists");
		} else {
			mapper.map(productsDto, productsEntity);
			CategoriesEntity categoriesEntity = categoryRepo
					.findByCatName(productsEntity.getCategoriesEntity().getCatName());
			productsEntity.setCategoriesEntity(categoriesEntity);
			return new ResponseEntity<>(productsRepo.save(productsEntity), HttpStatus.CREATED);
		}

	}

	public List<ProductsEntity> getAllProducts() {
		// logger.info("get all products called");
		return productsRepo.findAll();
	}

	@Override
	public ProductsEntity findByProdId(String prodId) {
		return productsRepo.findById(prodId).get();
	}

	@Override
	public ProductsEntity findByProdName(String prodName) {
		return productsRepo.findByProdName(prodName);
	}

}
