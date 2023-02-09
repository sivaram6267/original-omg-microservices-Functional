package com.lancesoft.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.Authorities;
import com.lancesoft.entity.RegistrationEntity;

import feign.Headers;

@FeignClient(name="OMG-PROFILE-SERVICE", path ="/user")
@Headers("Content-Type: application/json")
public interface ProfileProxy {

	@GetMapping("/myProfile/{userName}")
	public RegistrationEntity findByUserName(@PathVariable String userName);

	@GetMapping("/findByRole/{role}")
	public Authorities findByRole(@PathVariable String role);

	@GetMapping("/findByAuthorities")
	public List<RegistrationEntity> findByAuthorities(@RequestBody Authorities authorities);
	
	@GetMapping("/findByAddressUserName/{userName}")
	public List<AddressEntity> findByAddressUserName(@PathVariable String userName);
	
	@GetMapping("/findByAddressId")
	public AddressEntity findByAddressId(@RequestHeader String addressId);
	
}
