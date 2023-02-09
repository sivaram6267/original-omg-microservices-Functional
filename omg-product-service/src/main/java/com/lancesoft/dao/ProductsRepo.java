package com.lancesoft.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lancesoft.entity.CategoriesEntity;
import com.lancesoft.entity.ProductsEntity;

public interface ProductsRepo extends JpaRepository<ProductsEntity, String> {

	Boolean existsByProdName(String pName);

	ProductsEntity findByProdName(String pName);

	List<ProductsEntity> findByCategoriesEntity(CategoriesEntity categoriesEntity);

	ProductsEntity findByProdId(String pName);

	@Query("SELECT p FROM ProductsEntity p WHERE " + "p.prodName LIKE CONCAT('%',:query, '%')"
			+ "Or p.categoriesEntity.catName LIKE CONCAT('%', :query, '%')")
	List<ProductsEntity> searchProducts(@Param("query") String query);

	boolean existsByProdId(String prodId);
	
}
