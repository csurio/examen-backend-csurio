package com.assessment.bank.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BALANCE", 
       uniqueConstraints = {
    		   @UniqueConstraint(name = "CLIENT_ACCOUNT_UK", columnNames = {"account", "client_id"})
})
public class BalanceEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATEDDATE", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "ACCOUNT", length = 20, nullable = false)
    private String account;

    @Column(name = "BALANCE", nullable = false, precision = 8, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

}
