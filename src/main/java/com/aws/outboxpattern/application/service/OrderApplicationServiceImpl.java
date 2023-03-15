package com.aws.outboxpattern.application.service;

import com.aws.outboxpattern.application.dto.CreateOrderCommand;
import com.aws.outboxpattern.application.dto.CreateOrderResponse;
import com.aws.outboxpattern.infrastructure.dynamodb.persistence.OrderCreateCommandHandler;

public class OrderApplicationServiceImpl implements OrderApplicationService {

	@Override
	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) throws Exception {

		OrderCreateCommandHandler handler = new OrderCreateCommandHandler();
		return handler.createOrder(createOrderCommand);
	}

}
