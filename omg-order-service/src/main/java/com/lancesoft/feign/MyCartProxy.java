package com.lancesoft.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lancesoft.entity.MyCartList;

@FeignClient(name = "OMG-CART-SERVICE",path = "/api/user")
public interface MyCartProxy {

	@GetMapping("/findByCartListId/{cartId}")
	public MyCartList findByCartListId(@PathVariable String cartId);
		
	@GetMapping("/findByCartListByUserName/{userName}")
	public MyCartList findByCartListByUserName(@PathVariable String userName );
}
