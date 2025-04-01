package com.assessment.bank.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.bank.presentation.dto.TransactionCreateDto;
import com.assessment.bank.presentation.dto.TransactionHistoryDto;
import com.assessment.bank.presentation.dto.TransactionResponseDto;
import com.assessment.bank.service.TransactionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */

@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transactions", description = "Operations on client accounts")
@RequiredArgsConstructor
public class TransactionController {
	
	 private final TransactionService transactionService;
	
	@PostMapping("/process")
    public ResponseEntity<TransactionResponseDto> postTransaction(@RequestBody TransactionCreateDto request) {
		TransactionResponseDto response = transactionService.processTransaction(request);
        return ResponseEntity.ok(response);
    }
	
	 @GetMapping("/history")
    public ResponseEntity<TransactionHistoryDto> getHistory(
            @RequestParam Long clientId,
            @RequestParam(required = false) String account) {
        return ResponseEntity.ok(
            transactionService.getHistory(clientId, account)
        );
    }

}
