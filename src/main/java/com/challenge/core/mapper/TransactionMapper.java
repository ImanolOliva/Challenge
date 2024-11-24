package com.challenge.core.mapper;

import com.challenge.core.controller.response.ResponseTransactionDTO;
import com.challenge.core.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Map;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);




    @Mapping(target = "senderId", source = "senderId")
    @Mapping(target = "recipientId", source = "recipientId")
    @Mapping(target = "note", source = "note")
    @Mapping(target = "id", ignore = true)
    PeerToPeer toPeerToPeer(Transaction t, String senderId, String recipientId, String note);

    @Mapping(target = "mmcCode", source = "mccCode")
    @Mapping(target = "merchant", source = "merchant")
    @Mapping(target = "id", ignore = true)
    Credit toCredit(Transaction t, Number mccCode, Map<String,String> merchant);

    @Mapping(target = "bankCode", source = "bankCode")
    @Mapping(target = "recipientAccount", source = "recipientAccount")
    @Mapping(target = "id", ignore = true)
    Transfer toTransfer(Transaction t, String bankCode, String recipientAccount);


    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "cuil", source = "cuil")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "status", source = "status")
    ResponseTransactionDTO toTransaction(Transaction t, String amount, String cuil, String status,String message);

    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "cuil", source = "cuil")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "status", source = "status")
    ResponseTransactionDTO toTransaction(Transaction t, BigDecimal amount, String cuil, String status, String simbol, String message);

    @Mapping(target = "status", source = "status")
    @Mapping(target = "currency", source =  "currency")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "id", ignore = true)
    TransactionLogError toTransactionLogError(Transaction transaction);



}

