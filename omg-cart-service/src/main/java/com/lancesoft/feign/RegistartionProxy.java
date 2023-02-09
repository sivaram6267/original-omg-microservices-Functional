package com.lancesoft.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lancesoft.entity.RegistrationEntity;

@FeignClient(name="OMG-PROFILE-SERVICE", path="/user")
public interface RegistartionProxy {

	@GetMapping("/myProfile/{userName}")
	public RegistrationEntity findByUserName(@PathVariable String userName);
}
