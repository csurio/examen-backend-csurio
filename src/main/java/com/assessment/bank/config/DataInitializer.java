package com.assessment.bank.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.assessment.bank.persistence.entity.BalanceEntity;
import com.assessment.bank.persistence.entity.BalanceTransactionEntity;
import com.assessment.bank.persistence.entity.ClientEntity;
import com.assessment.bank.persistence.repository.BalanceRepository;
import com.assessment.bank.persistence.repository.BalanceTransactionRepository;
import com.assessment.bank.persistence.repository.ClientRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository 				clientRepository;
    private final BalanceRepository 		   balanceRepository;
    private final BalanceTransactionRepository transactionRepository;

    @Override
    public void run(String... args) {
        if (clientRepository.count() > 0) {
            log.info("Sample data already exists. Skipping data initialization.");
            return;
        }

        log.info("Inserting initial test data...");

        ClientEntity client1 = ClientEntity.builder()
                .name("John")
                .lastname("Doe")
                .birthday(LocalDate.of(1990, 5, 20))
                .email("john.doe@example.com")
                .phone("123456789")
                .address("123 Main St")
                .build();

        client1 = clientRepository.save(client1);

        BalanceEntity balance1 = BalanceEntity.builder()
                .account("ACC123")
                .balance(new BigDecimal("1000.00"))
                .createdDate(LocalDateTime.now())
                .client(client1)
                .build();

        balance1 = balanceRepository.save(balance1);

        transactionRepository.saveAll(List.of(
                BalanceTransactionEntity.builder()
                        .createdDate(LocalDateTime.now().minusDays(5))
                        .amount(new BigDecimal("200.00"))
                        .account(balance1.getId())
                        .client(client1)
                        .build(),
                BalanceTransactionEntity.builder()
                        .createdDate(LocalDateTime.now().minusDays(3))
                        .amount(new BigDecimal("-50.00"))
                        .account(balance1.getId())
                        .client(client1)
                        .build()
        ));

        // Cliente 2
        ClientEntity client2 = ClientEntity.builder()
                .name("Jane")
                .lastname("Smith")
                .birthday(LocalDate.of(1985, 8, 10))
                .email("jane.smith@example.com")
                .phone("987654321")
                .address("456 Market St")
                .build();

        client2 = clientRepository.save(client2);

        BalanceEntity balance2a = BalanceEntity.builder()
                .account("ACC456")
                .balance(new BigDecimal("750.00"))
                .createdDate(LocalDateTime.now())
                .client(client2)
                .build();

        BalanceEntity balance2b = BalanceEntity.builder()
                .account("ACC789")
                .balance(new BigDecimal("1500.00"))
                .createdDate(LocalDateTime.now())
                .client(client2)
                .build();

        balance2a = balanceRepository.save(balance2a);
        balance2b = balanceRepository.save(balance2b);

        transactionRepository.saveAll(List.of(
                BalanceTransactionEntity.builder()
                        .createdDate(LocalDateTime.now().minusDays(2))
                        .amount(new BigDecimal("500.00"))
                        .account(balance2a.getId())
                        .client(client2)
                        .build(),
                BalanceTransactionEntity.builder()
                        .createdDate(LocalDateTime.now().minusDays(1))
                        .amount(new BigDecimal("-250.00"))
                        .account(balance2b.getId())
                        .client(client2)
                        .build()
        ));

        log.info("Initial data inserted successfully.");
    }
}
