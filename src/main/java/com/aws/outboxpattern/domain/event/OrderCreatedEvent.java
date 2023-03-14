package com.aws.outboxpattern.domain.event;

import java.util.Date;

import com.aws.outboxpattern.model.Order;

public class OrderCreatedEvent extends OrderEvent {

	public OrderCreatedEvent(Order order, Date createdAt) {
		super(order, createdAt);
	}

}
