package com.aws.outboxpattern.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

	private  Long productId;

	private  Integer quantity;

	private  BigDecimal price;

}
