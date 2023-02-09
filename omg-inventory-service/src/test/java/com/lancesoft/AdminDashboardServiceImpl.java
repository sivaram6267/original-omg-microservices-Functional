//package com.lancesoft;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.lancesoft.dao.CategoryRepo;
//import com.lancesoft.dao.ProductsRepo;
//import com.lancesoft.dao.RegistrationRepo;
//import com.lancesoft.entity.AdminRegistrationEntity;
//import com.lancesoft.entity.CategoriesEntity;
//import com.lancesoft.entity.ProductsEntity;
//import com.lancesoft.entity.RegistrationEntity;
//import com.lancesoft.payload.ForgotPasswordPayload;
//
//@Service
//public class AdminDashboardServiceImpl {
//	@Autowired
//	CategoryRepo repo;
//
////	JwtUtil jwtUtil = new JwtUtil();
//
//	
//
//	@Test
//	public void addCategory() {
//		CategoriesEntity categoriesEntity = new CategoriesEntity("CAT001", "Vegetables");
//		CategoryRepo categoryRepo = Mockito.mock(CategoryRepo.class);		
//		when(categoryRepo.save(categoriesEntity)).thenReturn(categoriesEntity);
//		assertEquals(categoriesEntity, categoryRepo.save(categoriesEntity));
//	}
//
//	@Test
//	public void getMyProfile() {
//		AdminRegistrationDao registrationRepo2 = Mockito.mock(AdminRegistrationDao.class);
//		AdminRegistrationEntity reg = new AdminRegistrationEntity();
//		reg.setFirstName("abc");
//		reg.setLastName("nallavelli");
//		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcmlAZ21haWwuY29tIiwiaWF0IjoxNjU4NDcxNjc4LCJleHAiOjE2NTg1MDc2Nzh9.k-13EwVILJym-7t0UVwgVIFRqa5feGVpw7Vj-TaDKnM";
//		String userName = jwtUtil.extractUsername(token);
//		Mockito.when(registrationRepo2.findByUserName(userName)).thenReturn(reg);
//		if (jwtUtil.isTokenExpired(token)) {
//			reg.setFirstName(null);
//		} else {
//
//		}
//		assertEquals(registrationRepo2.findByUserName("sri@gmail.com"), reg);
//
//	}
//
//	@Test
//	public void updateProfile() {
//		AdminRegistrationDao registrationRepo2 = Mockito.mock(AdminRegistrationDao.class);
//		AdminRegistrationEntity inputdata = new AdminRegistrationEntity();
//		inputdata.setEmail("abc1234@gmail.com");
//		AdminRegistrationEntity outputData = new AdminRegistrationEntity();
//		outputData.setEmail("123456789@gmail.com");
//		outputData.setEmail(inputdata.getEmail());
//		Mockito.when(registrationRepo2.save(outputData)).thenReturn(outputData);
//		assertEquals(registrationRepo2.save(outputData), inputdata);
//	}
//
//	@Test
//	public void updateProduct() {
//		ProductsEntity productsEntity = new ProductsEntity();
//		productsEntity.setProdName("vegetable");
//		productsEntity.setProdId("1");
//		ProductsEntity entity = new ProductsEntity();
//		entity.setProdId("2");
//		entity.setProdName("vegetable");
//		ProductsRepo productsRepo = Mockito.mock(ProductsRepo.class);
//		Mockito.when(productsRepo.findByProdName(productsEntity.getProdName())).thenReturn(productsEntity);
//		ProductsEntity entity3 = productsRepo.findByProdName(productsEntity.getProdName());
//		productsEntity.setProdId(entity.getProdId());
//		productsRepo.save(productsEntity);
//		assertEquals(productsEntity, entity);
//
//	}
//
//	@Test
//	public void getAllCategory() {
//		CategoryRepo categoryRepo = Mockito.mock(CategoryRepo.class);
//		CategoriesEntity categoriesEntity = new CategoriesEntity();
//		categoriesEntity.setCatId("CAT0001");
//		categoriesEntity.setCatName("Vegatables");
//		CategoriesEntity categoriesEntity3 = new CategoriesEntity();
//		categoriesEntity3.setCatId("CAT0002");
//		categoriesEntity3.setCatName("Fruits");
//		List<CategoriesEntity> categoriesEntity2 = new ArrayList<>();
//		categoriesEntity2.add(categoriesEntity3);
//		categoriesEntity2.add(categoriesEntity);
//		Mockito.when(categoryRepo.findAll()).thenReturn(categoriesEntity2);
//		assertEquals(categoryRepo.findAll(), categoriesEntity2);
//	}
//
//	@Test
//	public void getAllProducts() {
//		ProductsRepo productsRepo = Mockito.mock(ProductsRepo.class);
//		ProductsEntity productsEntity = new ProductsEntity();
//		productsEntity.setProdId("PROD0001");
//		productsEntity.setProdName("Potato");
//		ProductsEntity productsEntity2 = new ProductsEntity();
//		productsEntity2.setProdId("PROD0002");
//		productsEntity2.setProdName("Carrot");
//		List<ProductsEntity> productsEntities = new ArrayList<>();
//		productsEntities.add(productsEntity);
//		productsEntities.add(productsEntity2);
//		Mockito.when(productsRepo.findAll()).thenReturn(productsEntities);
//		assertEquals(productsRepo.findAll(), productsEntities);
//	}
//
//	@Test
//	public void getoneCategory() {
//		CategoryRepo categoryRepo = Mockito.mock(CategoryRepo.class);
//		ProductsRepo productsRepo = Mockito.mock(ProductsRepo.class);
//		String catName = "Vegetables";
//		CategoriesEntity categoriesEntity3 = new CategoriesEntity();
//		categoriesEntity3.setCatId("CAT0001");
//		categoriesEntity3.setCatName(catName);
//		Mockito.when(categoryRepo.findByCatName(catName)).thenReturn(categoriesEntity3);
//		ProductsEntity prdent = new ProductsEntity();
//		prdent.setProdName("abc");
//		List<ProductsEntity> prd = new ArrayList<>();
//		prd.add(prdent);
//		Mockito.when(productsRepo.findByCategoriesEntity(categoriesEntity3)).thenReturn(prd);
//		CategoriesEntity categoriesEntity = categoryRepo.findByCatName(catName);
//		List<ProductsEntity> entity = productsRepo.findByCategoriesEntity(categoriesEntity);
//		assertEquals(prd, productsRepo.findByCategoriesEntity(categoriesEntity3));
//	}
//
//	@Test
//	public void forgotPassword() {
//
//		RegistrationRepo registrationRepo = Mockito.mock(RegistrationRepo.class);
//		ForgotPasswordPayload forgotPasswordPayload = new ForgotPasswordPayload("srikanth@gmail.com", "123456", "654321");
//		RegistrationEntity entity = new RegistrationEntity();
//		entity.setPassword("123456");
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		Mockito.when(registrationRepo.findByUserName(forgotPasswordPayload.getUserName())).thenReturn(entity);
//
//		RegistrationEntity registrationEntity1 = registrationRepo.findByUserName(forgotPasswordPayload.getUserName());
//
//		encoder.matches(forgotPasswordPayload.getOldPassword(), registrationEntity1.getPassword());
//		registrationEntity1.setPassword(encoder.encode(forgotPasswordPayload.getNewPassword()));
//		registrationRepo.save(registrationEntity1);
//
//		assertEquals(encoder.matches(forgotPasswordPayload.getNewPassword(), registrationEntity1.getPassword()), true);
//
//	}
//
//}
