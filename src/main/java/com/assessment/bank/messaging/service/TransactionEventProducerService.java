package com.assessment.bank.messaging.service;

import com.assessment.bank.messaging.dto.TransactionEventDto;
import com.assessment.bank.messaging.dto.TransactionResponseEventDto;

/**
 * @author Carlos M. Surio
 * @since  2 abr 2025
 * @version 1.0
 */
public interface TransactionEventProducerService {
	void sendTransactionEvent(TransactionEventDto         event);
	void sendTransactionEvent(TransactionResponseEventDto event);
}
