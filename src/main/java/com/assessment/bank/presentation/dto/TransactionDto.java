package com.assessment.bank.presentation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Long          transactionId;
    private LocalDateTime createdDate;
    private Long          account;
    private BigDecimal    amount;
}
