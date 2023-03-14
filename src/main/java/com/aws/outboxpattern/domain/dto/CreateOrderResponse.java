package com.aws.outboxpattern.domain.dto;

import java.util.UUID;

import com.aws.outboxpattern.domain.valueobject.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponse {
    
    private final UUID orderTrackingId;
    
    private final OrderStatus orderStatus;
    
    private final String message;

}