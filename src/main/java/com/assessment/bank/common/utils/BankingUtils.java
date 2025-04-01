package com.assessment.bank.common.utils;

import com.assessment.bank.common.constants.BankConstants;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
public class BankingUtils {
	
	/**
     * Generates a unique and irreversible account number.
     * The format is: {accountTypeId}{randomDigits}{office}{countryCode}
     *
     * @param accountTypeId The ID of the account type.
     * @param customerId    The ID of the customer.
     * @return A unique 11-digit account number.
     */
    public static String generateAccountNumber(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("CustomerId must not be null");
        }

        // Ensure accountTypeId is max 2 digits
//        String accountTypeStr = String.format("%02d", accountTypeId % 100);

        // Generate a 3-digit unique hash based on customerId
        int customerHash = Math.abs(customerId.hashCode() % 1000);
        String customerHashStr = String.format("%03d", customerHash);

        // Generate a 2-digit random number for uniqueness
        String randomDigits = String.format("%02d", BankConstants.RANDOM.nextInt(100));

        // Combine all parts to form the 11-digit account number
        return BankConstants.BANK_CODE + BankConstants.PNATURAL + BankConstants.COUNTRY_SLV + randomDigits + customerHashStr;
    }

}
