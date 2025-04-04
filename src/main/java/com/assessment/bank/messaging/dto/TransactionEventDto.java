package com.assessment.bank.messaging.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Carlos M. Surio
 * @since  2 abr 2025
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEventDto {
    private Long          clientId;
    private String        account;
    private BigDecimal    amount;
    private LocalDateTime timestamp;
    private String        status;
}
