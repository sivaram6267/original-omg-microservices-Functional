
package com.lancesoft.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lancesoft.entity.MyCart;
import com.lancesoft.entity.ProductsEntity;

public interface MyCartRepo extends JpaRepository<MyCart, String> {

	List<MyCart> findByUserId(String userName);

	MyCart findByCartId(String userName);

	MyCart findByProductsId(String userName);

	//List<MyCart> findByProductsEntity(ProductsEntity productsEntity);

//	@Query("from MyCart where userName=?1 and ProductsEntity=?2")
//	List<MyCart> findByUserNameANDProduct(String userName, ProductsEntity productsEntity);
}
