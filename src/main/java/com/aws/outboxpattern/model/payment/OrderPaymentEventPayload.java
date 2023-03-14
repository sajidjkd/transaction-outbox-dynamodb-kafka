package com.aws.outboxpattern.model.payment;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderPaymentEventPayload {

    @JsonProperty
    private String orderId;
    @JsonProperty
    private String customerId;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private Date createdAt;
    @JsonProperty
    private String paymentOrderStatus;
}
