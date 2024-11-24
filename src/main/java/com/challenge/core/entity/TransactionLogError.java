package com.challenge.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transaction_log_error")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLogError {

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
    private LocalDate createdAt;
    @Column(name = "cuit")
    private String cuit;
    @NotNull
    private String codeTransaction;
}
