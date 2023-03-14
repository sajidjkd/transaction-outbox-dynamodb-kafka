package com.aws.outboxpattern.infrastructure.kafka;

import software.amazon.lambda.powertools.tracing.Tracing;

import java.util.Properties;

public interface KafkaProducerPropertiesFactory {

    @Tracing
    Properties getProducerProperties();
}
