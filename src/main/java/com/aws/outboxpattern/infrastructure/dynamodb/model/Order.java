package com.aws.outboxpattern.infrastructure.dynamodb.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	private String customerId;
	private String orderId;
	private String orderedDateTime;
	private BigDecimal price;
	
	@DynamoDbPartitionKey
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@DynamoDbSortKey
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	

	public String getOrderedDateTime() {
		return orderedDateTime;
	}

	public void setOrderedDateTime(String orderedDateTime) {
		this.orderedDateTime = orderedDateTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	

}
