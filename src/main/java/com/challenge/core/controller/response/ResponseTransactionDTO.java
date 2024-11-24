package com.challenge.core.controller.response;
import com.challenge.core.entity.Status;
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
public class ResponseTransactionDTO {

    private String amount;
    private String cuil;
    private String status;
    private String message;
}
