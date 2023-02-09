package com.lancesoft.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lancesoft.customexception.CustomException;
import com.lancesoft.entity.OrdersEntity;
import com.lancesoft.service.UserDashboardService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserDashboardController {

	@Autowired
	UserDashboardService userDashboardService;

	@PostMapping(path = "/payWithCod")
	public OrdersEntity checkout(@RequestHeader String userName) {
		return userDashboardService.payWithCod(userName);
	}

	@GetMapping("/getMyOrders")
	public List<OrdersEntity> findMyOrders(@RequestHeader String userName) {
		return userDashboardService.findMyOrders(userName);
	}
	
	@GetMapping("/generatePdf/{orderId}")
	public void generatePDF(HttpServletResponse response, @PathVariable("orderId") String OrderId) throws IOException {
		try {
			response.setContentType("application/pdf");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());
			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
			response.setHeader(headerKey, headerValue);
			userDashboardService.export(response, OrderId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(e.getLocalizedMessage(),null);
		}
	}
	
	//localhost:2022/api/user/cancelOrders?orderId=ORD149
	@GetMapping("/cancelOrders")
	public String cancelOrders(@RequestParam("orderId") String orderId) {
		return userDashboardService.cancelOrders(orderId);
	}

}
