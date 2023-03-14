package com.aws.outboxpattern.domain.service;

import com.aws.outboxpattern.domain.dto.CreateOrderCommand;
import com.aws.outboxpattern.domain.dto.CreateOrderResponse;

public interface OrderApplicationService {

	CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) throws Exception;

}
