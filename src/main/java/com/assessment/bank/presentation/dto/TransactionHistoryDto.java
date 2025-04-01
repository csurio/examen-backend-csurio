package com.assessment.bank.presentation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryDto {

	private ClientDto            client;
	private List<BalanceDto>     balances;
	private List<TransactionDto> transactions;
}
