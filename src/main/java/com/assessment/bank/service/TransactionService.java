package com.assessment.bank.service;

import com.assessment.bank.presentation.dto.TransactionCreateDto;
import com.assessment.bank.presentation.dto.TransactionHistoryDto;
import com.assessment.bank.presentation.dto.TransactionResponseDto;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
public interface TransactionService {
	TransactionResponseDto processTransaction(TransactionCreateDto request);
	TransactionHistoryDto  getHistory(Long clientId, String account); 
}
