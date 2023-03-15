package com.aws.outboxpattern.application.service;

import com.aws.outboxpattern.application.dto.CreateOrderCommand;
import com.aws.outboxpattern.application.dto.CreateOrderResponse;

public interface OrderApplicationService {

	CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) throws Exception;

}
