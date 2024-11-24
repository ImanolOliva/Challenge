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
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO implements Payment {

    private String cardId;
    private String userId;
    @NotNull
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime createdAt;
    private String methodPayment;
    private Map<String,String> merchant;
    private Number mccCode;
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
    public CardDTO mapData(RequestTransactionDTO requestTransactionDTO) {
        setCardId(requestTransactionDTO.getCardId());
        setAmount(requestTransactionDTO.getAmount());
        setCurrency(requestTransactionDTO.getCurrency());
        setStatus("");
        setCreatedAt(LocalDateTime.now());
        setMethodPayment(requestTransactionDTO.getType());
        setUserId(requestTransactionDTO.getUserId());
        setMccCode(1);
        setMerchant(Map.of("name","amazon","merchand_id","5411"));
        setCuil(requestTransactionDTO.getCuil());

        return new CardDTO(
               cardId,
                userId,
                amount,
                currency,
                status,
                createdAt,
                methodPayment,
                merchant,
                mccCode,
                cuil
        );
    }

}
