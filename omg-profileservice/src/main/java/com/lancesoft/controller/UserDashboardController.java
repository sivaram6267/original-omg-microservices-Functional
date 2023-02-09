package com.lancesoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.dto.AddressDto;
import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.Authorities;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.service.UserDashboardService;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/user")
//@CrossOrigin("*")
@RefreshScope
public class UserDashboardController {

	@Autowired 
	UserDashboardService userDashboardService;
	
	
	@Retry(name = "profile-service")
	@GetMapping("/myProfile")
	public RegistrationEntity myProfile(@RequestHeader String userName ) {	
			//String userName = authentication.getName();
			return userDashboardService.getMyProfile(userName);		
	}
	
	
	@Retry(name = "profile-service")
	@PutMapping("/updateprofile")
	public ResponseEntity<?> updatedProfile(@RequestBody RegistrationEntity entity) {
		try {
			return userDashboardService.updateProfile(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("exception occured", HttpStatus.BAD_REQUEST);
		}
	}
	
	@Retry(name = "profile-service")
	@PostMapping("/addAddress")
	public AddressEntity addAddress(@RequestBody AddressDto addressdto) {
		return userDashboardService.addAddress(addressdto);
	}

	

	@Retry(name = "profile-service")
	@PostMapping("/addnewaddress")
	public AddressEntity addNewAddress(@RequestBody AddressDto addressdto) {
		String userName =null;;
		return userDashboardService.addNewAddress(addressdto, userName);
	}
	
	@Retry(name = "profile-service")
	@GetMapping("/findByAddressUserName/{userName}")
	public List<AddressEntity> findAddressByUserName(@PathVariable String userName) {
		System.out.println(userDashboardService.findAddressByUserName(userName));
		return userDashboardService.findAddressByUserName(userName);
	}


	@Retry(name = "profile-service")
	@GetMapping("/findByRole")
	public Authorities findByRole(String role) {
		return userDashboardService.findByRole(role);
		
	}

	@GetMapping("/findByAuthorities")
	public List<RegistrationEntity> findByAuthorities(Authorities authorities) {
		return userDashboardService.findByAuthorities(authorities);
	}
	
	@GetMapping("/myProfile/{userName}")
	public RegistrationEntity findByUserName(@PathVariable String userName) {
		return userDashboardService.findByUserName(userName);	
	}

	@GetMapping(path="/findByAddressId")
	public AddressEntity findByAddressId(@RequestHeader String addressId) {
		return userDashboardService.findByAddressId(addressId);
	}
	
	
}
