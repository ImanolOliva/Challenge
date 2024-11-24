package com.challenge.core.utils.loader;

import com.challenge.core.entity.Currency;
import com.challenge.core.entity.PaymentMethod;
import com.challenge.core.entity.Status;
import com.challenge.core.repository.CurrencyRepository;
import com.challenge.core.repository.PaymentMethodRepository;
import com.challenge.core.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private  CurrencyRepository currencyRepository;
    @Autowired
    private  PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private  StatusRepository statusRepository;



    @Override
    public void run(String... args) throws Exception {
        insertBD();
    }

    public void insertBD() {
        Currency currencyUSD = new Currency();
        currencyUSD.setDescription("USD");
        currencyUSD.setSimbol("US$");
        currencyRepository.save(currencyUSD);

        Currency currencyYuan = new Currency();
        currencyYuan.setDescription("YUAN");
        currencyYuan.setSimbol("¥");
        currencyRepository.save(currencyYuan);

        Currency currencyEUR = new Currency();
        currencyEUR.setDescription("EUR");
        currencyEUR.setSimbol("€");
        currencyRepository.save(currencyEUR);

        Currency currencyARS = new Currency();
        currencyARS.setDescription("ARS");
        currencyARS.setSimbol("$");
        currencyRepository.save(currencyARS);

        PaymentMethod paymentMethodCredit = new PaymentMethod();
        paymentMethodCredit.setDescription("CREDIT");
        paymentMethodRepository.save(paymentMethodCredit);

        PaymentMethod paymentMethodTransfer = new PaymentMethod();
        paymentMethodTransfer.setDescription("TRANSFER");
        paymentMethodRepository.save(paymentMethodTransfer);

        PaymentMethod paymentMethodPeerToPeer = new PaymentMethod();
        paymentMethodPeerToPeer.setDescription("PEER_TO_PEER");
        paymentMethodRepository.save(paymentMethodPeerToPeer);

        Status statusCompleted = new Status();
        statusCompleted.setDescription("COMPLETED");
        statusRepository.save(statusCompleted);

        Status statusIncompleted = new Status();
        statusIncompleted.setDescription("ERROR");
        statusRepository.save(statusIncompleted);

        Status statusPending = new Status();
        statusPending.setDescription("PENDING");
        statusRepository.save(statusPending);
    }
}
