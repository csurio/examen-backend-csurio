package com.assessment.bank.service.impl;

import com.assessment.bank.presentation.dto.TransactionCreateDto;
import com.assessment.bank.presentation.dto.TransactionHistoryDto;
import com.assessment.bank.presentation.dto.TransactionResponseDto;
import com.assessment.bank.service.TransactionService;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
public class TransactionServiceImpl implements TransactionService {

	@Override
	public TransactionResponseDto processTransaction(TransactionCreateDto request) {
		return null;
	}

	@Override
	public TransactionHistoryDto getHistory(Long clientId, String account) {
		return null;
	}

}
