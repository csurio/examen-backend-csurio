package com.assessment.bank.presentation.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TransactionCreateDto {

    @NotNull(message = "clientId is required")
    private Long clientId;

    @NotBlank(message = "account is required")
    private String account;

    @NotNull(message = "amount is required")
    @Digits(integer = 10, fraction = 2, message = "amount must be a valid number with up to 2 decimal places")
    private BigDecimal amount;

    @AssertTrue(message = "amount must be different from zero")
    public boolean isAmountNotZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) != 0;
    }
}

