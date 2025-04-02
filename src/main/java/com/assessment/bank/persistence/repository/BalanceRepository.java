package com.assessment.bank.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.bank.persistence.entity.BalanceEntity;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
public interface BalanceRepository extends JpaRepository<BalanceEntity, Long> {
	
	Optional<BalanceEntity> findByAccountAndClientId(String account, Long clientId);
	List<BalanceEntity> findAllByClientId(Long clientId);
	Optional<BalanceEntity> findByAccount(String account);
	
}
