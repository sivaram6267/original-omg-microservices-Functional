package com.lancesoft.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lancesoft.customexception.RegistrationCustomException;
import com.lancesoft.dao.RegistrationRepo;
import com.lancesoft.dto.RegistrationDto;
import com.lancesoft.entity.Authorities;
import com.lancesoft.entity.RegistrationEntity;

@Service
public class AdminService {

	@Autowired
	RegistrationRepo registrationRepo;

	public RegistrationEntity addReg(RegistrationDto registrationdto) {

		ModelMapper mapper = new ModelMapper();
		RegistrationEntity registrationEntity = new RegistrationEntity();

		if (registrationdto == null) {
			throw new RuntimeException("null found in registration please check");
		} else if (registrationRepo.existsByUserName(registrationdto.getUserName())) {
			throw new RegistrationCustomException( "Username Already Exists please enter unique");
		} else

		{

			mapper.map(registrationdto, registrationEntity);
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

			registrationEntity.setPassword(bCryptPasswordEncoder.encode(registrationEntity.getPassword()));
			Authorities authority = new Authorities();
			authority.setRole("ADMIN");
			List<Authorities> authorities = new ArrayList<Authorities>();
			authorities.add(authority);
			registrationEntity.setAuthorities(authorities);
			registrationRepo.save(registrationEntity);
			return registrationEntity;
		}

	}

}
