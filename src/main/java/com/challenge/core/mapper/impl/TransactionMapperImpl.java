package com.challenge.core.mapper.impl;

import com.challenge.core.controller.response.ResponseTransactionDTO;
import com.challenge.core.entity.*;
import com.challenge.core.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public PeerToPeer toPeerToPeer(Transaction t, String senderId, String recipientId, String note) {
        return null;
    }


    @Override
    public Credit toCredit(Transaction t, Number mccCode, Map<String, String> merchant) {
        return null;
    }

    @Override
    public Transfer toTransfer(Transaction t, String bankCode, String recipientAccount) {
        return null;
    }

    @Override
    public ResponseTransactionDTO toTransaction(Transaction t, String amount, String dni, String status, String message) {
        return null;
    }

    @Override
    public ResponseTransactionDTO toTransaction(Transaction t, BigDecimal amount, String cuil, String status, String simbol, String message) {
       String amountSave = "";
        if(amount != null){
            amountSave = simbol.concat(String.valueOf(amount));
        }
        return TransactionMapper.INSTANCE.toTransaction(t,amountSave,cuil,status,message);
    }


    @Override
    public TransactionLogError toTransactionLogError(Transaction transaction) {
        return null;
    }


}
