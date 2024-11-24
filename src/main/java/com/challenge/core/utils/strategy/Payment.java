package com.challenge.core.utils.strategy;
import com.challenge.core.controller.request.RequestTransactionDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Payment {

    BigDecimal getAmount();
    LocalDateTime getCreatedAt();
    String cuil();
    Object mapData(RequestTransactionDTO requestTransactionDTO);

}
