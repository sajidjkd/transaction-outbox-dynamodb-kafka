package com.aws.outboxpattern.domain.event;

import java.util.Date;

import com.aws.outboxpattern.infrastructure.dynamodb.model.Order;

public abstract class OrderEvent implements DomainEvent<Order> {
	private final Order order;
	private final Date createdAt;

	public OrderEvent(Order order, Date createdAt) {
		this.order = order;
		this.createdAt = createdAt;
	}

	public Order getOrder() {
		return order;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}
