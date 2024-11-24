package com.challenge.core.controller;


import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.entity.Transaction;
import com.challenge.core.service.impl.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CurrencyController {


    @Autowired
    private  CurrencyServiceImpl currencyService;


    @RequestMapping(
            value = "/coins",
            method = RequestMethod.GET)
    public ResponseEntity<ResponseMessageDTO> getCoins() throws Exception {
        try{
            return this.currencyService.getAllCurrency();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
