package com.lancesoft.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.lancesoft.dto.AddToCartDto;
import com.lancesoft.dto.AddressDto;
import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.MyCart;
import com.lancesoft.entity.MyCartList;
import com.lancesoft.entity.OrdersEntity;
import com.lancesoft.entity.ProductsEntity;
import com.lowagie.text.DocumentException;

public interface UserDashboardService {

	

	OrdersEntity payWithCod(String cartId);


	List<OrdersEntity> findMyOrders(String userName);


	void export(HttpServletResponse response, String orderId);


	String cancelOrders(String orderId);





}
