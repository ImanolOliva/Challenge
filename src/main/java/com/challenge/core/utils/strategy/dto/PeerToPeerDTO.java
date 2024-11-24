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
public class PeerToPeerDTO implements Payment {

    private String senderId;
    private String userId;
    @NotNull
    private BigDecimal amount;
    private String currency;
    private String state;
    private LocalDateTime createdAt;
    private String paymentMethod;
    private String recipientId;
    private String note;
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
    public PeerToPeerDTO mapData(RequestTransactionDTO requestTransactionDTO) {
        setSenderId(requestTransactionDTO.getSenderId());
        setUserId(requestTransactionDTO.getUserId());
        setAmount(requestTransactionDTO.getAmount());
        setCurrency(requestTransactionDTO.getCurrency());
        setState("");
        setCreatedAt(LocalDateTime.now());
        setPaymentMethod(requestTransactionDTO.getType());
        setRecipientId(requestTransactionDTO.getRecipientId());
        setNote("Transaction Send");
        setCuil(requestTransactionDTO.getSenderId());

        return new PeerToPeerDTO(
                senderId,
                userId,
                amount,
                currency,
                state,
                createdAt,
                paymentMethod,
                recipientId,
                note,
                cuil
        );
    }
}