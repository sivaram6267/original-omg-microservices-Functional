//package com.lancesoft;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import org.springframework.stereotype.Service;
//
//import com.lancesoft.dao.AddressRepo;
//import com.lancesoft.dao.RegistrationRepo;
//import com.lancesoft.entity.RegistrationEntity;
//import com.lancesoft.jwt.JwtUtil;
//
//@Service
//public class UserDashboardServiceImpl {
//
//	JwtUtil jwtUtil;
//
//	RegistrationRepo registrationRepo = Mockito.mock(RegistrationRepo.class);
//
//	AddressRepo addressRepo = Mockito.mock(AddressRepo.class);
//
//	
//
//	@Test
//	public void getMyProfile() {
//		
//		String userName = "srikanth@gmail.com";
//		RegistrationEntity  registrationEntity=new RegistrationEntity();
//		registrationEntity.setUser_id("USER00001");
//		registrationEntity.setFirstName("Srikanth");
//		when(registrationRepo.findByUserName(userName)).thenReturn(registrationEntity);
//			//return new ResponseEntity(registrationRepo.findByUserName(userName), HttpStatus.OK);
//		assertEquals(registrationRepo.findByUserName(userName), registrationEntity);
//	}
//
//
//}