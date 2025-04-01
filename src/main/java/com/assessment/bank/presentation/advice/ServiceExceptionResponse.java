package com.assessment.bank.presentation.advice;

import java.time.LocalDateTime;
import java.util.Map;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceExceptionResponse {
	private LocalDateTime       timestamp;
    private int                 status;
    private String              error;
    private String              message;
    private String              path;
    private Map<String, String> validationErrors;
}
