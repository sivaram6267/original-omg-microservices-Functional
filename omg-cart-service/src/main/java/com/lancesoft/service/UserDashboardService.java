package com.lancesoft.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lancesoft.dto.AddToCartDto;
import com.lancesoft.dto.MyCartListDto;
import com.lancesoft.entity.MyCart;
import com.lancesoft.entity.MyCartList;

public interface UserDashboardService {

	
	ResponseEntity<?> addToCart(AddToCartDto addToCartDto, String userName);

	MyCartList myCartList(MyCartList myCartList, String userName);

	String deleteCartItem(String cartId);
	
	MyCartList updateCart(String cartId, Double qty, MyCartList cartList, String userName);

	MyCartList findByCartListId(String cartId);

	MyCartList findByCartListByUserName(String cartId);

	String deleteMyCartList(String userName);

	


}
