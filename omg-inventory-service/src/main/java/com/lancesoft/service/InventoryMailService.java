package com.lancesoft.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class InventoryMailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	SimpleMailMessage mailMessage;

	public void sendMail(String to,String ProductName) {


		
		try {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			message.setFrom(new InternetAddress("onlinesupermarket37@gmail.com"));
			message.setTo(to);
			message.setSubject("Alert Your Inventory Stock is Low !");
			message.setText(" <!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "  <head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\r\n" + "   \r\n"
					+ "    </head>\r\n" + "  <body>\r\n"
					+ "      <html> <p> Alert Your Inventory Stock is Low </p>\r\n"
					+ "<center><img style=\"; width:150px; height: 50px;\" src='cid:image' alt=\"test\"/></center>\r\n"
					+ "<h2><center style=\'font-weight: bold\'>Low Quantity in Products</center> <font color=#ff4500><center>"
					+ ProductName + "</center></font></h2><p><center>quantity is low please maintain stock.</center></p></font>\r\n"
					+ "<footer>If you didn't try to sign in just now, please <nobr style=\"color: rgb(9, 132, 209); \"> change your password</nobr> to protect your account.</footer>\r\n"
					+ "<hr >\r\n" + "<p>Sent by <font style=\"color: blue\"> OMG</font> </p>\r\n"
					+ "<p><font style=\"color: blue\"> Support Center Privacy Policy</font> </p>\r\n" + "</html>\r\n"
					+ "      </body>\r\n" + "</html>", true);

			message.addInline("image", new ClassPathResource("omg.png"));

			// Send message
			javaMailSender.send(mimeMessage);
			System.out.println("message sent successfully....");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
