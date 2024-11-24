package com.challenge.core.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class RequestTransactionDTO {

    private String currency;
    private String type;
    private BigDecimal amount;
    private String userId;
    private String cardId;
    private String cuil;
    private String recipientAccount;
    private String senderId;
    private String recipientId;


}
