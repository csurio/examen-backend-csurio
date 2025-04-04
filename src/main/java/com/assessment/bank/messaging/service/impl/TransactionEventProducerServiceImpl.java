package com.assessment.bank.messaging.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.assessment.bank.messaging.dto.TransactionEventDto;
import com.assessment.bank.messaging.dto.TransactionResponseEventDto;
import com.assessment.bank.messaging.service.TransactionEventProducerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Carlos M. Surio
 * @since  2 abr 2025
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionEventProducerServiceImpl implements TransactionEventProducerService {

	@Value("${app.kafka.topics.transactions}")
    private String topic;
	private final KafkaTemplate<String, Object> kafkaTemplate;
	
	@Async
    public void sendTransactionEvent(TransactionEventDto event) {
		sendEvent(event, "Transaction");
    }

	@Async
	public void sendTransactionEvent(TransactionResponseEventDto event) {
		sendEvent(event, "TransactionResponse");
	}
	
	private <T> void sendEvent(T event, String type) {
		log.info("Kafka brokers: {}", kafkaTemplate.getProducerFactory().toString());
	    log.info("Sending {} event to Kafka topic '{}': {}", type, topic, event);
	    kafkaTemplate.send(topic, event)
        .thenAccept(result -> log.info("{} event sent successfully to topic '{}': {}", type, topic, result.getRecordMetadata()))
        .exceptionally(ex -> {
            log.error("Failed to send {} event to Kafka topic '{}'. Error: {}", type, topic, ex.getMessage(), ex);
            return null;
        });
	}

}
