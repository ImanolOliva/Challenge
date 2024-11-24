package com.challenge.core.service.impl;


import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.entity.Currency;
import com.challenge.core.repository.CurrencyRepository;
import com.challenge.core.service.CurrencyService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public ResponseEntity<ResponseMessageDTO> getAllCurrency() {
        try{
            ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
            responseMessageDTO.setData(this.currencyRepository.findAll());
            responseMessageDTO.setMessage("Currency: ");
            return new ResponseEntity(responseMessageDTO,HttpStatus.ACCEPTED);
        } catch (InvalidDataAccessApiUsageException e) {
            log.error("Error connecting bd: {}", e.getMessage());
            ResponseMessageDTO responseMessage = new ResponseMessageDTO("Error connectiong bd", e.getMessage());
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Unexpected error", e.getMessage());
            ResponseMessageDTO responseMessage = new ResponseMessageDTO("Unexpected error", null);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Currency getCurrency(String description) throws Exception {
        try {
            return this.currencyRepository.findByDescription(description.toUpperCase());
        } catch (Exception e) {
            log.info("Error get currency: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
