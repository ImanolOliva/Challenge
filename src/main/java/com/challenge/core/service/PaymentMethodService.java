package com.challenge.core.service;

import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.entity.PaymentMethod;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

public interface PaymentMethodService {

     PaymentMethod findByDescription(@Param("description") String description);

     ResponseEntity<ResponseMessageDTO> getAllPaymentMethod();


}
