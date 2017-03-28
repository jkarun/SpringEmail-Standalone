package com.arun.service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.arun.model.ProductOrder;

@Service("mailService")
public class MailServiceImpl implements MailService{

	@Autowired
	JavaMailSender mailSender;
	
	public void sendEmail(Object object) {
		ProductOrder order = (ProductOrder) object;
		try {
			MimeMessagePreparator mime = getMessagePreparator(order);
			mailSender.send(mime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private MimeMessagePreparator getMessagePreparator(final ProductOrder order) {
		MimeMessagePreparator prep = new MimeMessagePreparator() {
//			sasi.haldurai1993@gmail.com
			public void prepare(MimeMessage mail) throws Exception {
				mail.setFrom("jkarun63@gmail.com");
				mail.setRecipient(Message.RecipientType.TO, new InternetAddress(order.getCustomerInfo().getEmail()));
				mail.setText("Dear " + order.getCustomerInfo().getName()
						+ ", thank you for placing order. Your order id is " + order.getOrderId() + ".");
				mail.setSubject("Your order on Demoapp");
			}
		};
		
		return prep;
		
	}
}
