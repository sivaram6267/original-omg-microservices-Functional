//package com.example.demo.feign;
//
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import com.example.demo.entity.RegistrationEntity;
//
//import feign.Headers;
//
//@FeignClient(name="OMG-PROFILE-SERVICE", path ="/user")
//@Headers("Content-Type: application/json")
//public interface ProfileProxy {
//
//	@GetMapping("/myProfile/{userName}")
//	public RegistrationEntity findByUserName(@PathVariable String userName);
//
//	
//	
//
//	
//}
//
