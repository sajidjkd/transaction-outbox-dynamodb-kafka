package com.aws.outboxpattern.model;

import com.aws.outboxpattern.domain.valueobject.OrderStatus;
import com.aws.outboxpattern.domain.valueobject.OutboxStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentOutbox {
	
    private String id;
    private String orderId;
    private String createdAt;
    private String processedAt;
    private String type;
    private String payload;
    private OrderStatus orderStatus;
    private OutboxStatus outboxStatus;
    private int version;
    
    @DynamoDbPartitionKey
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getProcessedAt() {
		return processedAt;
	}
	public void setProcessedAt(String processedAt) {
		this.processedAt = processedAt;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public OutboxStatus getOutboxStatus() {
		return outboxStatus;
	}
	public void setOutboxStatus(OutboxStatus outboxStatus) {
		this.outboxStatus = outboxStatus;
	}
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
}
