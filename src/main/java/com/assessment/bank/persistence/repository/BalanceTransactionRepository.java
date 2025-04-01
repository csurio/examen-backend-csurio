package com.assessment.bank.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.bank.persistence.entity.BalanceTransactionEntity;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
public interface BalanceTransactionRepository extends JpaRepository<BalanceTransactionEntity, Long> {

}
