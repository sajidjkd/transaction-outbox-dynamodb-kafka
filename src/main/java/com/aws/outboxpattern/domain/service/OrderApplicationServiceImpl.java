package com.aws.outboxpattern.domain.service;

import com.aws.outboxpattern.domain.dto.CreateOrderCommand;
import com.aws.outboxpattern.domain.dto.CreateOrderResponse;

public class OrderApplicationServiceImpl implements OrderApplicationService {

	@Override
	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) throws Exception {

		OrderCreateCommandHandler handler = new OrderCreateCommandHandler();
		return handler.createOrder(createOrderCommand);
	}

}
