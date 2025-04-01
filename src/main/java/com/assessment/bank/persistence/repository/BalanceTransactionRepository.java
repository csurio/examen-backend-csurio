package com.assessment.bank.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assessment.bank.persistence.entity.BalanceTransactionEntity;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
public interface BalanceTransactionRepository extends JpaRepository<BalanceTransactionEntity, Long> {

	List<BalanceTransactionEntity> findAllByClientIdAndAccountId(Long clientId, Long accountId);

	@Query("  SELECT t "
		   +"   FROM BalanceTransactionEntity t "
		   + " WHERE t.client.id  = :clientId "
		   + "   AND t.account   IN :accountIds")
	List<BalanceTransactionEntity> findAllByClientIdAndAccountIds(@Param("clientId")   Long clientId,
	                                                              @Param("accountIds") List<Long> accountIds);


}
