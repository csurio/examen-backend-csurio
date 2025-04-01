package com.assessment.bank.presentation.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.assessment.bank.common.exception.BadRequestException;
import com.assessment.bank.common.exception.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class ServiceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ServiceExceptionResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        log.error("Resource Not Found: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }
	
	@ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ServiceExceptionResponse> handleBadRequestException(
    		BadRequestException ex, WebRequest request) {
        log.error("Bad request: {}: {}", ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServiceExceptionResponse> handleBadRequestException(
    		MethodArgumentNotValidException ex, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        log.warn("Validation error: {}", errors);
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, errors);
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceExceptionResponse> handleBadRequestException(Exception ex, WebRequest request) {
		log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request, null);
    }
	
	
	private ResponseEntity<ServiceExceptionResponse> buildResponse(
			HttpStatus          status, 
			String              message, 
			WebRequest          request,
			Map<String, String> errors) {
		
		ServiceExceptionResponse response = ServiceExceptionResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(status.value())
				.error(status.getReasonPhrase())
				.message(message)
				.path(request.getDescription(false))
				.validationErrors(errors)
				.build();
	
        return ResponseEntity.status(status).body(response);
	}

}
