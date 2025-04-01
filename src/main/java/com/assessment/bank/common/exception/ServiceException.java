package com.assessment.bank.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Carlos M. Surio
 * @since  4 feb 2025
 * @version 1.0
 */
@Getter
@Setter
public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private final String errorCode;
	
	public ServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
