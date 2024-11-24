package com.challenge.core.controller;


import com.challenge.core.controller.request.RequestTransactionDTO;
import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.entity.Transaction;
import com.challenge.core.controller.response.ResponseTransactionDTO;
import com.challenge.core.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;



    @RequestMapping(
            value = "/createTransaction",
            method = RequestMethod.POST)
    public ResponseEntity<ResponseTransactionDTO> createTransaction( @Validated @RequestBody
                                                    List<RequestTransactionDTO> createTransactionDTO) throws Exception {
        try {
            return transactionService.createTransaction(createTransactionDTO,false);
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }
    }
    @RequestMapping(
            value = "/createTransactionError",
            method = RequestMethod.POST)
    public ResponseEntity<ResponseTransactionDTO> createTransactionError( @Validated @RequestBody
                                                                     List<RequestTransactionDTO> createTransactionDTO) throws Exception {
        try {
            return transactionService.createTransaction(createTransactionDTO,true);
        } catch (Exception e) {
            throw new Exception(e.getCause());
        }
    }

    @RequestMapping(
            value = "/status/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<ResponseMessageDTO> getStatus(@PathVariable("id") Long id) throws Exception {
        try {
          return  transactionService.getStatusTransaction(id);
        }catch (Exception e){
            throw new Exception(e);
        }
    }


    @RequestMapping(
            value = "/transactions/{cuit}",
            method = RequestMethod.GET)
    public ResponseEntity<ResponseMessageDTO> getTransactionByUser(
        @PathVariable("cuit") String cuit,
        Pageable pageable,
        @RequestParam(value = "startDate",required = false) String startDate,
        @RequestParam(value = "endDate",required = false) String endDate) throws Exception {
        try{
            return this.transactionService.getTransactionByUser(cuit,pageable,startDate,endDate);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(
            value = "/reversTransactions"
            ,method = RequestMethod.POST)
    public  ResponseEntity<ResponseMessageDTO> reverseTransactions() throws Exception {
        try{
          return   this.transactionService.reverseTransaction();
        }catch (Error e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(
            value = "/transactionsHistory",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getAllHistoryTransactions
            (@RequestParam(value = "status",required = false)String status,
             Pageable pageable,
             @RequestParam(value = "startDate", required = false) String startDate,
             @RequestParam(value = "endDate", required = false) String endDate) throws Exception {
        try{
           return  this.transactionService.getAllTransactions(status,pageable,startDate,endDate);
        }catch (Exception e){
          throw  new Exception(e.getMessage());
        }
    }

    @RequestMapping(
            value = "/transactionsHistoryStatus",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getAllHistoryTransactionsStatus
            (@RequestParam(value = "description")String description,
             Pageable pageable,
             @RequestParam(value = "startDate", required = false) String startDate,
             @RequestParam(value = "endDate", required = false) String endDate) throws Exception {
        try{
            return  this.transactionService.getAllTransactionsByStatus(description,pageable,startDate,endDate);
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }




}
