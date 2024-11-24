package com.challenge.core.utils.strategy.dto;

import com.challenge.core.controller.request.RequestTransactionDTO;
import com.challenge.core.utils.strategy.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO implements Payment {


    private String bank_code;
    private String recipientAccount;
    private String userId;
    @NotNull
    private BigDecimal amount;
    private String currency;
    private String state;
    private String methodPayment;
    private LocalDateTime createdAt;
    @NotNull
    private String cuil;

    @Override
    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String cuil() {
        return cuil;
    }



    @Override
    public TransferDTO mapData(RequestTransactionDTO requestTransactionDTO) {
        setBank_code("1234");
        setAmount(requestTransactionDTO.getAmount());
        setCurrency(requestTransactionDTO.getCurrency());
        setState("");
        setCreatedAt(LocalDateTime.now());
        setMethodPayment(requestTransactionDTO.getType());
        setRecipientAccount(requestTransactionDTO.getRecipientAccount());
        setUserId(requestTransactionDTO.getUserId());
        setCuil(requestTransactionDTO.getCuil());
        return new TransferDTO(
                bank_code,
                recipientAccount,
                userId,
                amount,
                currency,
                state,
                methodPayment,
                createdAt,
                cuil
        );
    }



}