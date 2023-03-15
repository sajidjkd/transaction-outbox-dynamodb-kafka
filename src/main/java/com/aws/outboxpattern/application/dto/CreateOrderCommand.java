package com.aws.outboxpattern.application.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderCommand {

	private  String customerId;
	private  BigDecimal price;
	private  List<OrderItemDto> items;

	public static void main(String args[]) {
		ObjectMapper mapper = new ObjectMapper();

		OrderItemDto oi = OrderItemDto.builder().price(new BigDecimal(2.0)).productId(1L).quantity(1).build();
		List<OrderItemDto> list = new ArrayList<>();
		list.add(oi);
		CreateOrderCommand coc = CreateOrderCommand.builder().items(list).customerId(UUID.randomUUID().toString()).price(new BigDecimal(2)).build();
		try {
			System.out.println(mapper.writeValueAsString(coc));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
