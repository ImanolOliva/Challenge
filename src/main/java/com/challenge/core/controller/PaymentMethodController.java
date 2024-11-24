package com.challenge.core.controller;


import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class PaymentMethodController {


    @Autowired
    private PaymentMethodService paymentMethodService;


    @RequestMapping(
            value = "/paymentMethods",
            method = RequestMethod.GET)
    public ResponseEntity<ResponseMessageDTO> getPaymentMethod() throws Exception {
        try{
            return this.paymentMethodService.getAllPaymentMethod();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
