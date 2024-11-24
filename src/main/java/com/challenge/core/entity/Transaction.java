package com.challenge.core.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    private Long id;
    private String userId;
    @NotNull
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "cuit")
    @NotNull
    @Pattern(regexp = "^\\d{11}$", message = "El CUIT debe  tener 11 dígitos")
    @Min(value = 20000000000L, message = "El CUIT debe ser mayor o igual a 20.000.000.000")
    @Max(value = 29999999999L, message = "El CUIT debe ser menor o igual a 29.999.999.999")
    private String cuit;
    private String codeTransaction;
}
