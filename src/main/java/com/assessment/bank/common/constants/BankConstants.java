package com.assessment.bank.common.constants;

import java.util.Random;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
public class BankConstants {
	
	public static final String SYSTEM_USER = "system";
	
	// Tipos de Cuentas
    public static final String AHO         = "01";
    public static final String CTE         = "02";
    public static final String TCR         = "03";
    public static final String LOAN        = "04";
    public static final String TDB         = "05";
    
    //Tipo de Cliente
    public static final String PNATURAL    = "01";
    public static final String PJURIDICA   = "02";
    
    //Codigos de Oficina
    public static final String BANK_CODE   = "7";
    
    public static final String COUNTRY_SLV = "001";
    public static final String COUNTRY_BRA = "002";
    public static final String COUNTRY_CHI = "003";
    public static final String COUNTRY_COL = "004";
    public static final String COUNTRY_CRI = "005";
    public static final String COUNTRY_GUY = "006";
    public static final String COUNTRY_PAN = "007";
    public static final String COUNTRY_PER = "008";
    public static final String COUNTRY_VEN = "009";
    
    public static final Random RANDOM = new Random();

}
