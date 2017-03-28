package com.arun.config;

/*
 * Important: In case you are getting problems while connecting to Gmail, 
 * check first if you have two step security enabled in your gmail. Additionally,
 * check if you have received an email from provider[gmail] saying “Sign-in attempt prevented”. 
 * If yes, there will be instruction in that email on how to allow/disallow less secured apps to 
 * access your mail provider [Turn on/off access to less secured apps]. 
 * 
 * If you get authendication error even if your credentials are valid try following setting in gmail
 *	gmail account -> settings -> security -> lesssecureapps -> turn off Access for less secure apps
 */

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.arun.model.CustomerInfo;
import com.arun.model.ProductOrder;
import com.arun.service.OrderService;

public class ApplicationBoot {
	public static void main(String [] asd){
		AbstractApplicationContext app = new AnnotationConfigApplicationContext(EmailConfig.class);

		OrderService orderService = (OrderService) app.getBean("orderService");
		orderService.sendOrderConfirmation(getDummyOrder());
		((AbstractApplicationContext) app).close();
		
	}
	public static ProductOrder getDummyOrder(){
		ProductOrder order = new ProductOrder();
		order.setOrderId("0001");
		order.setProductName("Thinkpad T510");
		order.setStatus("confirmed");
		
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setName("Arun jk");
		customerInfo.setAddress("street address");
		customerInfo.setEmail("jkarun63@gmail.com");
		order.setCustomerInfo(customerInfo);
		return order;
	}
}
