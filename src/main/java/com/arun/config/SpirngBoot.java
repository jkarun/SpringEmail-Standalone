package com.arun.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.arun.model.CustomerInfo;
import com.arun.model.ProductOrder;
import com.arun.service.OrderService;

public class SpirngBoot {
	public static void main(String [] asd){
		AbstractApplicationContext app = new AnnotationConfigApplicationContext(EmailConfig.class);
		

		OrderService orderService = (OrderService) app.getBean("orderService");
		orderService.sendOrderConfirmation(getDummyOrder());
		((AbstractApplicationContext) app).close();
		
	}
	public static ProductOrder getDummyOrder(){
		ProductOrder order = new ProductOrder();
		order.setOrderId("1111");
		order.setProductName("Thinkpad T510");
		order.setStatus("confirmed");
		
		CustomerInfo customerInfo = new CustomerInfo();
		customerInfo.setName("agi");
		customerInfo.setAddress("Coimbatore");
		customerInfo.setEmail("jkarun63@gmail.com");
		order.setCustomerInfo(customerInfo);
		return order;
	}
}
/*
 * If you get authendication error even if your credentials are valid try following setting in gmail
 *	gmail account -> settings -> security -> lesssecureapps -> turn off Access for less secure apps
 */