package com.challenge.core.utils;


import com.challenge.core.entity.*;
import com.challenge.core.mapper.impl.TransactionMapperImpl;
import com.challenge.core.repository.StatusRepository;
import com.challenge.core.repository.TransactionLogErrorRepository;
import com.challenge.core.service.impl.TransactionServiceImpl;
import com.challenge.core.utils.strategy.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.challenge.core.utils.IConstanst.COMPLETED;
import static com.challenge.core.utils.IConstanst.ERROR;

@Component
@Slf4j
public class Utils {


    @Autowired
    TransactionMapperImpl transactionMapperImpl;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TransactionLogErrorRepository transactionLogErrorRepository;

    public Status getStatus(String description) throws Exception {
        try {
            return this.statusRepository.findByDescription(description);
        } catch (Exception e) {
            log.info("Error get currency: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public Transaction setCommonTransactionFields(Transaction transaction, Payment paymentData, Currency currency, PaymentMethod paymentMethod) throws Exception {
        transaction.setAmount(paymentData.getAmount());
        transaction.setCuit(paymentData.cuil());
        transaction.setCreatedAt(paymentData.getCreatedAt());
        if(transaction.getCurrency() == null){
            transaction.setCurrency(new Currency());
            transaction.setPaymentMethod(new PaymentMethod());
            transaction.setStatus(new Status());
        }
        transaction.getCurrency().setId(currency.getId());
        transaction.getCurrency().setSimbol(currency.getSimbol());
        transaction.getPaymentMethod().setId(paymentMethod.getId());
        transaction.getStatus().setId(this.getStatus(COMPLETED).getId());
        transaction.setCodeTransaction(generateCode());
        return transaction;
    }

    public  String generateCode(){
        DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        long timeMilli = date.getTime();
        String aux =
                Long.toString(36).toUpperCase() +
        Long.toString(timeMilli,36).toUpperCase() +
                        Integer.toString((int)(Math.random()*1295),36).toUpperCase();
        String result = aux.replaceAll("(.{"+ 4 +"})","$1-");
        return result.substring(0,result.length()-1);
    }

}
