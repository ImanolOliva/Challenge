package com.challenge.core.controller.response;

import com.challenge.core.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDTO {
    private String message;
    private Object data;

}
