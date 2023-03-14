package com.aws.outboxpattern.domain.mapper;

import com.aws.outboxpattern.domain.event.OrderCreatedEvent;
import com.aws.outboxpattern.domain.valueobject.PaymentOrderStatus;
import com.aws.outboxpattern.model.payment.OrderPaymentEventPayload;

public class OrderDataMapper {
	
	  public OrderPaymentEventPayload orderCreatedEventToOrderPaymentEventPayload(OrderCreatedEvent orderCreatedEvent) {
	        return OrderPaymentEventPayload.builder()
	                .customerId(orderCreatedEvent.getOrder().getCustomerId())
	                .orderId(orderCreatedEvent.getOrder().getOrderId())
	                .price(orderCreatedEvent.getOrder().getPrice())
	                .createdAt(orderCreatedEvent.getCreatedAt())
	                .paymentOrderStatus(PaymentOrderStatus.PENDING.name())
	                .build();
	    }

}
