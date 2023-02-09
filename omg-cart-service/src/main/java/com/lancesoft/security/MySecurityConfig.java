//package com.lancesoft.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.BeanIds;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.header.writers.StaticHeadersWriter;
//
//import com.lancesoft.jwt.JwtFilter;
//
//@Configuration
//@EnableWebSecurity
//public class MySecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	MyUserDetails details;
//
//	@Autowired
//	private JwtFilter jwtFilter;
//
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//
//		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
//
//		dao.setUserDetailsService(details);
//		dao.setPasswordEncoder(new BCryptPasswordEncoder());
//		return dao;
//	}
//
//	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		///
//
//		System.out.println("http headers called");
//
//		http.headers()
//				.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers",
//						"Access-Control-Allow-Methods,Access-Control-Allow-Origin,Authorization"
//								+ "origin: http://10.81.4.184:3000")
//
//				);
//
//		http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "http://10.81.4.184:3000")
//
//		).addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS"));
//
//		//////
//		http.csrf().disable().authorizeRequests()
//				.antMatchers("/api/admin/getAllProducts","/api/admin/getCategories","/api/sendOtp","/api/verify","/api/user/getMyCartList", "/api/user/addToCart", "/api/user/register", "/api/sendOtp", "/user/searchProduct",
//						"/api/user/searchProduct", "/api/login","/api/getDeliveriesByDate/")
//				.permitAll().anyRequest().authenticated().and().exceptionHandling().and().sessionManagement();
//
//
//		
//		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//		http.cors();
//	}
//
//}