package com.assessment.bank.service.helper;

import com.assessment.bank.common.utils.BankingUtils;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
public class BalanceHelper {
	
	String getAccountNumber(Long customerId) {
		return BankingUtils.generateAccountNumber(customerId);
	}
}
