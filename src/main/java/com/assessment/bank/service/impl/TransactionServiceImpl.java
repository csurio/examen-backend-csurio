package com.assessment.bank.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assessment.bank.common.exception.ResourceNotFoundException;
import com.assessment.bank.persistence.entity.BalanceEntity;
import com.assessment.bank.persistence.entity.BalanceTransactionEntity;
import com.assessment.bank.persistence.entity.ClientEntity;
import com.assessment.bank.persistence.repository.BalanceRepository;
import com.assessment.bank.persistence.repository.BalanceTransactionRepository;
import com.assessment.bank.persistence.repository.ClientRepository;
import com.assessment.bank.presentation.dto.TransactionCreateDto;
import com.assessment.bank.presentation.dto.TransactionHistoryDto;
import com.assessment.bank.presentation.dto.TransactionResponseDto;
import com.assessment.bank.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	
	private final ClientRepository             clientRepository;
	private final BalanceRepository            balanceRepository;
	private final BalanceTransactionRepository transactionRepository;

	@Override
	public TransactionResponseDto processTransaction(TransactionCreateDto request) {
		log.info("Transaction received for client {} acount {}", request.getClientId(), request.getAccount());
		
		ClientEntity client = clientRepository.findById(request.getClientId())
	            .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
		
		BalanceEntity account = balanceRepository.findByAccountAndClientId(request.getAccount(), client.getId())
	            .orElseGet(() -> {
	                log.warn("Account {} does not exist. It will be create automatically.", request.getAccount());
	                BalanceEntity accountToCreate = BalanceEntity.builder()
	                        .account(request.getAccount())
	                        .balance(request.getAmount())
	                        .createdDate(LocalDateTime.now())
	                        .client(client)
	                        .build();
	                return balanceRepository.save(accountToCreate);
	            });
		
		if(Optional.ofNullable(account.getId()).isPresent()) {
			BigDecimal newBalance = account.getBalance().add(request.getAmount());
			account.setBalance(newBalance);
            balanceRepository.save(account);
		}
		
		BalanceTransactionEntity transaccion = BalanceTransactionEntity.builder()
                .createdDate(LocalDateTime.now())
                .amount(request.getAmount())
                .account(Long.valueOf(request.getAccount()))
                .client(client)
                .build();
        transactionRepository.save(transaccion);
        
        // Aqui se hara el  envío a RabbitMQ/Kafka (pendiente TODO)
        log.info("Transaction processed and saved. It will be sent to the message queue asynchronously.");

        return new TransactionResponseDto("Transaction received and is being processed", "00");
	}

	@Override
	public TransactionHistoryDto getHistory(Long clientId, String account) {
		return null;
	}

}
