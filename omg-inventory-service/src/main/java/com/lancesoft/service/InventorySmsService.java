package com.lancesoft.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

@Service
public class InventorySmsService {

	@Autowired
	HttpSession session;

//	@Autowired
//	RegistrationRepo registrationRepo;

	@Autowired
	BCryptPasswordEncoder encoder;

	String phoneNumber;

	private final String ACCOUNT_SID = "AC4c8b81d6389deeba0d1db6dde9cf9849";
	private final String token = "1c428ac58ee45e5c994fec9cdf8b0345";
	private final String trialNumber = "+16622462920";

	public void sendMessage(String phoneNumber, String productName) {

		this.phoneNumber = phoneNumber;

		if (phoneNumber != null)
			Twilio.init(ACCOUNT_SID, token);

		String message = "A product in the inventory is in low quantity .The product is " + productName;

		MessageCreator creator = Message.creator(new PhoneNumber(phoneNumber), new PhoneNumber(trialNumber), message);
		//System.out.println(phoneNumber);
		creator.create();

	}

}
