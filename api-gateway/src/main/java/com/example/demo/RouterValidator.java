package com.example.demo;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

	public static final List<String> openApiEndpoints = List.of("/**", "/api/login", "/api/admin/myProfile",
			 "/api/user/register", "/api/sendOtp", "/auth/register", "/api/login", "/admin/sendOtp",
			"/api/verify", "/api/admin/myProfile", "/api/admin/getCategories");

	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

}
