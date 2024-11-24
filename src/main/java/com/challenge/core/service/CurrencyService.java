package com.challenge.core.service;

import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.entity.Currency;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CurrencyService {

    ResponseEntity<ResponseMessageDTO> getAllCurrency();

    Currency getCurrency(String description) throws Exception;
}
