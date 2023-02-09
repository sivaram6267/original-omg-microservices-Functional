package com.lancesoft.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.lancesoft.entity.RegistrationEntity;

@FeignClient(name="OMG-PROFILE-SERVICE", path="/user")
public interface RegistartionProxy {

	@GetMapping("/myProfile")
	public RegistrationEntity findByUserName(String userName);
}
