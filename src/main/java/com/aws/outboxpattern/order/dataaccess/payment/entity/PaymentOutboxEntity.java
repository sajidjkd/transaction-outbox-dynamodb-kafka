package com.aws.outboxpattern.order.dataaccess.payment.entity;

//import com.food.ordering.system.domain.valueobject.OrderStatus;

//import com.food.ordering.system.outbox.OutboxStatus;
//import com.food.ordering.system.saga.SagaStatus;
//import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.aws.outboxpattern.domain.valueobject.OrderStatus;
import com.aws.outboxpattern.domain.valueobject.OutboxStatus;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean
public class PaymentOutboxEntity {

	private UUID id;
	// private UUID sagaId;
	private ZonedDateTime createdAt;
	private ZonedDateTime processedAt;
	private String type;
	private String payload;
	private OrderStatus orderStatus;
	private OutboxStatus outboxStatus;
	private int version;

}
