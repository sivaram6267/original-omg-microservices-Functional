package com.lancesoft.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.customexception.CustomException;
import com.lancesoft.customexception.RegistrationCustomException;
import com.lancesoft.dao.MyCartRepo;
import com.lancesoft.dto.AddToCartDto;
import com.lancesoft.entity.MyCart;
import com.lancesoft.entity.MyCartList;
import com.lancesoft.service.UserDashboardService;

@RestController
@RequestMapping("/api/user")
//@CrossOrigin("*")
public class CartController {

	@Autowired
	UserDashboardService userDashboardService;

	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCart(@RequestBody AddToCartDto addToCartDto, @RequestHeader String userName) {
		try {
			return userDashboardService.addToCart(addToCartDto, userName);
		} catch (Exception e) {
			throw new CustomException("error occured", e.getMessage());
		}
	}

	@GetMapping("/getMyCartList")
	public List<MyCartList> myCartList(MyCartList mycartList, @RequestHeader String userName) {
		try {

			return Arrays.asList(userDashboardService.myCartList(mycartList, userName));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RegistrationCustomException("error occured");
		}
	}

	@PostMapping("/updateMyCart")
	public MyCartList updateCart(@RequestParam("qty") Double qty, @RequestParam String cartId, MyCartList cartList) {
		try {
			String userName = null;
			return userDashboardService.updateCart(cartId, qty, cartList, userName);
		} catch (Exception e) {
			throw new RegistrationCustomException("error occured");
		}
	}

	@PostMapping("/delete")
	public String deleteCart(@RequestParam("cartId") String cartId) {
		return userDashboardService.deleteCartItem(cartId);
	}

	@GetMapping("/findByCartListId/{cartId}")
	public MyCartList findByCartListId(@PathVariable String cartId) {
		return userDashboardService.findByCartListId(cartId);
	}

	@GetMapping("/findByCartListByUserName/{userName}")
	public MyCartList findByCartListByUserName(@PathVariable String userName) {
		return userDashboardService.findByCartListByUserName(userName);
	}
	
	@PostMapping("/deleteMyCartList")
	public String deleteMyCartList(@RequestHeader String userName) {
		return userDashboardService.deleteMyCartList(userName);
	}
	
	
	@Autowired
	MyCartRepo cartRepo;
	
	@GetMapping("/getMyCart/{prodId}")
	public MyCart getMyCart(@PathVariable String prodId) {
		return cartRepo.findByProductsId(prodId);
	}


}
