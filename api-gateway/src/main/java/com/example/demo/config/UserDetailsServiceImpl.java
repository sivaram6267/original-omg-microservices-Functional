//package com.example.demo.config;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.example.demo.entity.RegistrationEntity;
//import com.example.demo.feign.ProfileProxy;
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//    private ProfileProxy repository;
//	
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<RegistrationEntity> userInfo = Optional.ofNullable(repository.findByUserName(username));
//        return userInfo.map(UserInfoUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
//	}
//
//}
