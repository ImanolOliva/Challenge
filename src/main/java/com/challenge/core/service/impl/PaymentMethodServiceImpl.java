package com.challenge.core.service.impl;

import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.entity.PaymentMethod;
import com.challenge.core.repository.PaymentMethodRepository;
import com.challenge.core.service.PaymentMethodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentMethod findByDescription(String description) {
       return this.paymentMethodRepository.findByDescription(description);
    }

    @Override
    public ResponseEntity<ResponseMessageDTO> getAllPaymentMethod(){
        try{
            ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
            responseMessageDTO.setData(this.paymentMethodRepository.findAll());
            responseMessageDTO.setMessage("Payment Method: ");
            return new ResponseEntity(responseMessageDTO, HttpStatus.ACCEPTED);
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


}
