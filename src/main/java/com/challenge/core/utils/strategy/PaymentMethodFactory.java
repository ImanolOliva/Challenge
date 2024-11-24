package com.challenge.core.utils.strategy;

import com.challenge.core.utils.strategy.dto.CardDTO;
import com.challenge.core.utils.strategy.dto.PeerToPeerDTO;
import com.challenge.core.utils.strategy.dto.TransferDTO;

public class PaymentMethodFactory {

        public static Payment getMapper(String paymentType) {
            switch (paymentType.toUpperCase()) {
                case "CREDIT":
                    return new CardDTO();
                case "TRANSFER":
                    return new TransferDTO();
                case "PEER_TO_PEER":
                    return new PeerToPeerDTO();
                default:
                    throw new IllegalArgumentException("Tipo de pago no soportado: " + paymentType);
            }
        }
    }

