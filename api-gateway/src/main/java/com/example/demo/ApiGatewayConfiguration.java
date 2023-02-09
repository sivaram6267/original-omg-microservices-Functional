package com.example.demo;

import java.time.Duration;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationFilter;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

@Configuration
public class ApiGatewayConfiguration {

	@Autowired
	AuthenticationFilter authenticationFilter;

	@Bean
	public RouteLocator gateWayLocator(RouteLocatorBuilder builder) {
		Function<PredicateSpec, Buildable<Route>> profileRoute = p -> p
				.path("/api/sendOtp", "/api/login", "/api/user/register", "/user/myProfile", "/user/myProfile/",
						"/user/addAddress")
				.filters(f -> f.filter(authenticationFilter)
						.circuitBreaker(c -> c.setName("custom").setFallbackUri("/fallback/testService")))
				.uri("lb://OMG-PROFILE-SERVICE");

		Function<PredicateSpec, Buildable<Route>> productRoute = p -> p
				.path("/api/admin/addProducts", "/api/admin/getonecategory/", "/api/admin/addCategory",
						"/admin/updateproduct", "/api/admin/getCategories", "/api/admin/getAllProducts",
						"/api/user/searchProduct")
				.filters(f -> f.filter(authenticationFilter)
						.circuitBreaker(c -> c.setName("custom").setFallbackUri("/fallback/testService")))
				.uri("lb://OMG-PRODUCT-SERVICE");

		Function<PredicateSpec, Buildable<Route>> cartRoute = p -> p
				.path("/api/user/addToCart", "/api/user/getMyCartList", "/user/deletecart", "/api/user/updateMyCart",
						"/api/user/findByCartListByUserName/")
				.filters(f -> f.filter(authenticationFilter)
						.circuitBreaker(c -> c.setName("custom").setFallbackUri("/fallback/testService")))
				.uri("lb://OMG-CART-SERVICE");

		Function<PredicateSpec, Buildable<Route>> inventory = p -> p
				.path("/api/admin/getallinventories", "/api/admin/addInventory")
				.filters(f -> f.filter(authenticationFilter)
						.circuitBreaker(c -> c.setName("custom").setFallbackUri("/fallback/testService")))
				.uri("lb://OMG-INVENTORY-SERVICE");

		Function<PredicateSpec, Buildable<Route>> ordersRoute = p -> p
				.path("/api/user/payWithCod", "/user/findMyOrders", "/api/user/pdf/generate/**")
				.filters(f -> f.filter(authenticationFilter)
						.circuitBreaker(c -> c.setName("custom").setFallbackUri("/fallback/testService")))
				.uri("lb://OMG-ORDER-SERVICE");

		Function<PredicateSpec, Buildable<Route>> paymentRoute = p -> p.path("/user/findMyOrders")
				.filters(f -> f.filter(authenticationFilter)
						.circuitBreaker(c -> c.setName("custom").setFallbackUri("/fallback/testService")))
				.uri("lb://OMG-PAYMENT-SERVICE");

		return builder.routes().route(profileRoute).route(productRoute).route(cartRoute).route(inventory)
				.route(ordersRoute).route(paymentRoute).build();

	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(
			CircuitBreakerRegistry circuitBreakerRegistry, TimeLimiterRegistry timeLimiterRegistry) {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.circuitBreakerConfig(circuitBreakerRegistry.getConfiguration("backendB")
						.orElse(circuitBreakerRegistry.getDefaultConfig()))
				.timeLimiterConfig(timeLimiterRegistry.getConfiguration("backendB")
						.orElse(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(30000)).build()))
				.build());
	}

//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer()
//    {
//        return factory->factory.configureDefault(id ->new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//                .timeLimiterConfig(TimeLimiterConfig.custom()
//                        .timeoutDuration(Duration.ofSeconds(20)).build()).build());
//    }

}
