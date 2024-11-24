package com.challenge.core.service;


import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.controller.response.ResponseTransactionDTO;
import com.challenge.core.entity.Transaction;
import com.challenge.core.controller.request.RequestTransactionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {

     ResponseEntity<ResponseTransactionDTO> createTransaction(List<RequestTransactionDTO> requestTransactionDTO,Boolean error);

     ResponseEntity<ResponseMessageDTO> getTransactionByUser(String cuit, Pageable page, String start, String end) throws Exception;

     ResponseEntity<ResponseMessageDTO> getStatusTransaction(Long id) throws Exception;


     ResponseEntity<ResponseMessageDTO> reverseTransaction() throws Exception;

     ResponseEntity<?> getAllTransactions(String status,Pageable pageable,String start,String end);

     ResponseEntity<?> getAllTransactionsByStatus(String description,Pageable pageable,String start,String end);
}
