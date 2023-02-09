package com.lancesoft.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lancesoft.dto.AddressDto;
import com.lancesoft.dto.JwtLoginDto;
import com.lancesoft.entity.AddressEntity;
import com.lancesoft.entity.Authorities;
import com.lancesoft.entity.RegistrationEntity;
import com.lancesoft.jwt.JwtTokenString;

@Service
public interface UserDashboardService {

	

	

	RegistrationEntity getMyProfile(String userName);

	ResponseEntity<?> updateProfile(RegistrationEntity registrationEntity);

	AddressEntity addAddress(AddressDto addressdto);

	AddressEntity updateAddress(AddressDto addressdto);

	AddressEntity addNewAddress(AddressDto addressdto, String userName);

	List<AddressEntity> findAddressByUserName(String userName);

	Authorities findByRole(String role);

	List<RegistrationEntity> findByAuthorities(Authorities authorities);

	List<AddressEntity> findByAddressUserName(String userName);

	RegistrationEntity findByUserName(String userName);

	AddressEntity findByAddressId(String addressId);

	JwtTokenString login(JwtLoginDto authRequest);

}
