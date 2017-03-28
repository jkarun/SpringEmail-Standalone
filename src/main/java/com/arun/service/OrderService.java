package com.arun.service;

import com.arun.model.ProductOrder;

public interface OrderService {
	public void sendOrderConfirmation(ProductOrder productOrder);
}
