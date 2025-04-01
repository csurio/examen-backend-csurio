package com.assessment.bank.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assessment.bank.common.exception.BadRequestException;
import com.assessment.bank.common.exception.ResourceNotFoundException;
import com.assessment.bank.persistence.entity.BalanceEntity;
import com.assessment.bank.persistence.entity.BalanceTransactionEntity;
import com.assessment.bank.persistence.entity.ClientEntity;
import com.assessment.bank.persistence.repository.BalanceRepository;
import com.assessment.bank.persistence.repository.BalanceTransactionRepository;
import com.assessment.bank.persistence.repository.ClientRepository;
import com.assessment.bank.presentation.dto.BalanceDto;
import com.assessment.bank.presentation.dto.ClientDto;
import com.assessment.bank.presentation.dto.TransactionCreateDto;
import com.assessment.bank.presentation.dto.TransactionDto;
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
			
			if (request.getAmount().signum() < 0 && newBalance.compareTo(BigDecimal.ZERO) < 0) {
	            throw new BadRequestException("Insufficient balance for debit transaction.");
	        }
			
			account.setBalance(newBalance);
            balanceRepository.save(account);
		}
		
		BalanceTransactionEntity transaccion = BalanceTransactionEntity.builder()
                .createdDate(LocalDateTime.now())
                .amount(request.getAmount())
                .account(account.getId())
                .client(client)
                .build();
        transactionRepository.save(transaccion);
        
        // Aqui se hara el  envío a RabbitMQ/Kafka (pendiente TODO)
        log.info("Transaction processed and saved. It will be sent to the message queue asynchronously.");

        return new TransactionResponseDto("Transaction received and is being processed", "00");
	}
	

	@Override
	public TransactionHistoryDto getHistory(Long clientId, String accountNumber) {
		log.info("Getting transaction history for client {} and account {}", clientId, accountNumber);
		
		ClientEntity client = clientRepository.findById(clientId)
		        .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
		
		List<BalanceEntity>            balances;
	    List<BalanceTransactionEntity> transactions;
		
		if (accountNumber != null && !accountNumber.isBlank()) {
	        BalanceEntity balance = balanceRepository.findByAccountAndClientId(accountNumber, clientId)
	                .orElseThrow(() -> new ResourceNotFoundException("Account not found for client"));

	        balances     = List.of(balance);
	        transactions = transactionRepository.findAllByClientIdAndAccount(clientId, balance.getId());
	    }
		else {
	        balances = balanceRepository.findAllByClientId(clientId);
	        List<Long> accountIds = balances.stream()
	                						.map(BalanceEntity::getId)
	                						.toList();
	        transactions = transactionRepository.findAllByClientIdAndAccountIds(clientId, accountIds);
	    }
		
		List<BalanceDto>     balanceDtos     = balances.stream()
	            								.map(b -> new BalanceDto(b.getAccount(), b.getBalance(), clientId))
	            								.toList();
		List<TransactionDto> transactionDtos = transactions.stream()
	            								.map(t -> new TransactionDto(t.getId(), t.getCreatedDate(), t.getAccount(), t.getAmount()))
	            								.toList();
		ClientDto            clientDto       = ClientDto.builder()
												.clientId(client.getId())
												.name(client.getName())
												.lastname(client.getLastname())
												.email(client.getEmail())
												.phone(client.getPhone())
												.account(accountNumber)
												.build();
		
		return TransactionHistoryDto.builder()
				.client(clientDto)
				.balances(balanceDtos)
				.transactions(transactionDtos)
				.build();
	}

}
