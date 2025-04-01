package com.assessment.bank.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Carlos M. Surio
 * @since  1 abr 2025
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long   clientId;
    private String name;
    private String lastname;
    private String email;
    private String phone;
    private String account;
}
