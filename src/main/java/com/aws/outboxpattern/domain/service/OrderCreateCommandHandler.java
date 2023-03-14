package com.aws.outboxpattern.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.aws.outboxpattern.client.DynamoDbConnection;
import com.aws.outboxpattern.domain.dto.CreateOrderCommand;
import com.aws.outboxpattern.domain.dto.CreateOrderResponse;
import com.aws.outboxpattern.domain.dto.OrderItemDto;
import com.aws.outboxpattern.domain.util.DateUtil;
import com.aws.outboxpattern.domain.valueobject.OutboxStatus;
import com.aws.outboxpattern.domain.valueobject.PaymentOrderStatus;
import com.aws.outboxpattern.model.Order;
import com.aws.outboxpattern.model.OrderItem;
import com.aws.outboxpattern.model.OrderPaymentOutbox;
import com.aws.outboxpattern.model.payment.OrderPaymentEventPayload;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.MappedTableResource;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactPutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.TransactWriteItemsEnhancedRequest.Builder;

@Slf4j
public class OrderCreateCommandHandler {

	private ObjectMapper mapper = new ObjectMapper();

	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) throws Exception {

		/*
		 * Write to DynamoDB tables order and paymentoutbox with transactional writes...
		 */

		try {

			Order order = buildOrder(createOrderCommand);

			TransactPutItemEnhancedRequest<Order> orderRequest = TransactPutItemEnhancedRequest.builder(Order.class)
					.item(order).build();

			List<OrderItem> orderItems = buildOrderItems(createOrderCommand, order.getOrderId());

			// Build transaction request
			Builder transactRequest = TransactWriteItemsEnhancedRequest.builder();
			transactRequest.addPutItem(getOrderTable(), orderRequest);

			for (OrderItem oi : orderItems) {

				transactRequest.addPutItem(getOrderItemTable(),
						TransactPutItemEnhancedRequest.builder(OrderItem.class).item(oi).build());

			}

			OrderPaymentEventPayload orderPaymentEventPayload = OrderPaymentEventPayload.builder().createdAt(new Date())
					.customerId(order.getCustomerId()).orderId(order.getOrderId())
					.paymentOrderStatus(PaymentOrderStatus.PENDING.name()).price(order.getPrice()).build();

			// add outbox details..
			OrderPaymentOutbox orderOutbox = OrderPaymentOutbox.builder().createdAt(DateUtil.getFormattedDate(new Date())).id(UUID.randomUUID().toString())
					.orderId(order.getOrderId()).outboxStatus(OutboxStatus.STARTED)
					.payload(mapper.writeValueAsString(orderPaymentEventPayload)).processedAt(DateUtil.getFormattedDate(new Date())).build();

			TransactPutItemEnhancedRequest<OrderPaymentOutbox> paymentOutboxRequest = TransactPutItemEnhancedRequest
					.builder(OrderPaymentOutbox.class).item(orderOutbox).build();

			transactRequest.addPutItem(getPaymentOutboxTable(), paymentOutboxRequest);

			DynamoDbConnection.getDynamoDbEnhancedClient().transactWriteItems(transactRequest.build());

		} catch (Exception e) {
			throw e;
		}

		return CreateOrderResponse.builder().build();
	}

	private Order buildOrder(CreateOrderCommand orderCommand) {
		if (null == orderCommand)
			return null;

		return Order.builder().orderId(UUID.randomUUID().toString()).customerId(orderCommand.getCustomerId())
				.orderedDateTime(DateUtil.getFormattedDate(new Date())).build();
	}

	private List<OrderItem> buildOrderItems(CreateOrderCommand orderCommand, String orderId) {
		if (null == orderCommand)
			return null;

		List<OrderItem> items = new ArrayList<>();

		for (OrderItemDto itemDto : orderCommand.getItems()) {
			items.add(OrderItem.builder().orderId(orderId).orderItemId(UUID.randomUUID().toString())
					.price(itemDto.getPrice()).productId(itemDto.getProductId()).quantity(itemDto.getQuantity())
					.build());
		}

		return items;
	}

	private MappedTableResource<Order> getOrderTable() {
		return DynamoDbConnection.getDynamoDbEnhancedClient().table("Order", TableSchema.fromBean(Order.class));
	}

	private MappedTableResource<OrderItem> getOrderItemTable() {
		return DynamoDbConnection.getDynamoDbEnhancedClient().table("OrderItem", TableSchema.fromBean(OrderItem.class));
	}

	private MappedTableResource<OrderPaymentOutbox> getPaymentOutboxTable() {
		return DynamoDbConnection.getDynamoDbEnhancedClient().table("OrderPaymentOutbox",
				TableSchema.fromBean(OrderPaymentOutbox.class));
	}

}
