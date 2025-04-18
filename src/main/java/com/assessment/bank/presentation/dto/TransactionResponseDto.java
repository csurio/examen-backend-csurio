package com.assessment.bank.presentation.dto;

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
public class TransactionResponseDto {
    private String message;
    private String code;
}