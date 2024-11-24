package com.challenge.core.mapper;

import com.challenge.core.controller.response.ResponseTransactionDTO;
import com.challenge.core.entity.Credit;
import com.challenge.core.entity.PeerToPeer;
import com.challenge.core.entity.Transaction;
import com.challenge.core.entity.TransactionLogError;
import com.challenge.core.entity.Transfer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-24T19:44:42-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
public class TransactionMapperImpl implements TransactionMapper {

    private final DatatypeFactory datatypeFactory;

    public TransactionMapperImpl() {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        }
        catch ( DatatypeConfigurationException ex ) {
            throw new RuntimeException( ex );
        }
    }

    @Override
    public PeerToPeer toPeerToPeer(Transaction t, String senderId, String recipientId, String note) {
        if ( t == null && senderId == null && recipientId == null && note == null ) {
            return null;
        }

        PeerToPeer peerToPeer = new PeerToPeer();

        if ( t != null ) {
            peerToPeer.setUserId( t.getUserId() );
            peerToPeer.setAmount( t.getAmount() );
            peerToPeer.setCurrency( t.getCurrency() );
            peerToPeer.setStatus( t.getStatus() );
            peerToPeer.setPaymentMethod( t.getPaymentMethod() );
            peerToPeer.setCreatedAt( t.getCreatedAt() );
            peerToPeer.setCuit( t.getCuit() );
            peerToPeer.setCodeTransaction( t.getCodeTransaction() );
        }
        if ( senderId != null ) {
            peerToPeer.setSenderId( senderId );
        }
        if ( recipientId != null ) {
            peerToPeer.setRecipientId( recipientId );
        }
        if ( note != null ) {
            peerToPeer.setNote( note );
        }

        return peerToPeer;
    }

    @Override
    public Credit toCredit(Transaction t, Number mccCode, Map<String, String> merchant) {
        if ( t == null && mccCode == null && merchant == null ) {
            return null;
        }

        Credit credit = new Credit();

        if ( t != null ) {
            credit.setUserId( t.getUserId() );
            credit.setAmount( t.getAmount() );
            credit.setCurrency( t.getCurrency() );
            credit.setStatus( t.getStatus() );
            credit.setPaymentMethod( t.getPaymentMethod() );
            credit.setCreatedAt( t.getCreatedAt() );
            credit.setCuit( t.getCuit() );
            credit.setCodeTransaction( t.getCodeTransaction() );
        }
        if ( mccCode != null ) {
            credit.setMmcCode( mccCode );
        }
        if ( merchant != null ) {
            Map<String, String> map = merchant;
            if ( map != null ) {
                credit.setMerchant( new HashMap<String, String>( map ) );
            }
        }

        return credit;
    }

    @Override
    public Transfer toTransfer(Transaction t, String bankCode, String recipientAccount) {
        if ( t == null && bankCode == null && recipientAccount == null ) {
            return null;
        }

        Transfer transfer = new Transfer();

        if ( t != null ) {
            transfer.setUserId( t.getUserId() );
            transfer.setAmount( t.getAmount() );
            transfer.setCurrency( t.getCurrency() );
            transfer.setStatus( t.getStatus() );
            transfer.setPaymentMethod( t.getPaymentMethod() );
            transfer.setCreatedAt( t.getCreatedAt() );
            transfer.setCuit( t.getCuit() );
            transfer.setCodeTransaction( t.getCodeTransaction() );
        }
        if ( bankCode != null ) {
            transfer.setBankCode( bankCode );
        }
        if ( recipientAccount != null ) {
            transfer.setRecipientAccount( recipientAccount );
        }

        return transfer;
    }

    @Override
    public ResponseTransactionDTO toTransaction(Transaction t, String amount, String cuil, String status, String message) {
        if ( t == null && amount == null && cuil == null && status == null && message == null ) {
            return null;
        }

        ResponseTransactionDTO responseTransactionDTO = new ResponseTransactionDTO();

        if ( amount != null ) {
            responseTransactionDTO.setAmount( amount );
        }
        if ( cuil != null ) {
            responseTransactionDTO.setCuil( cuil );
        }
        if ( status != null ) {
            responseTransactionDTO.setStatus( status );
        }
        if ( message != null ) {
            responseTransactionDTO.setMessage( message );
        }

        return responseTransactionDTO;
    }

    @Override
    public ResponseTransactionDTO toTransaction(Transaction t, BigDecimal amount, String cuil, String status, String simbol, String message) {
        if ( t == null && amount == null && cuil == null && status == null && simbol == null && message == null ) {
            return null;
        }

        ResponseTransactionDTO responseTransactionDTO = new ResponseTransactionDTO();

        if ( amount != null ) {
            responseTransactionDTO.setAmount( amount.toString() );
        }
        if ( cuil != null ) {
            responseTransactionDTO.setCuil( cuil );
        }
        if ( status != null ) {
            responseTransactionDTO.setStatus( status );
        }
        if ( message != null ) {
            responseTransactionDTO.setMessage( message );
        }

        return responseTransactionDTO;
    }

    @Override
    public TransactionLogError toTransactionLogError(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionLogError transactionLogError = new TransactionLogError();

        transactionLogError.setStatus( transaction.getStatus() );
        transactionLogError.setCurrency( transaction.getCurrency() );
        transactionLogError.setPaymentMethod( transaction.getPaymentMethod() );
        transactionLogError.setUserId( transaction.getUserId() );
        transactionLogError.setAmount( transaction.getAmount() );
        transactionLogError.setCreatedAt( xmlGregorianCalendarToLocalDate( localDateTimeToXmlGregorianCalendar( transaction.getCreatedAt() ) ) );
        transactionLogError.setCuit( transaction.getCuit() );
        transactionLogError.setCodeTransaction( transaction.getCodeTransaction() );

        return transactionLogError;
    }

    private static LocalDate xmlGregorianCalendarToLocalDate( XMLGregorianCalendar xcal ) {
        if ( xcal == null ) {
            return null;
        }

        return LocalDate.of( xcal.getYear(), xcal.getMonth(), xcal.getDay() );
    }

    private XMLGregorianCalendar localDateTimeToXmlGregorianCalendar( LocalDateTime localDateTime ) {
        if ( localDateTime == null ) {
            return null;
        }

        return datatypeFactory.newXMLGregorianCalendar(
            localDateTime.getYear(),
            localDateTime.getMonthValue(),
            localDateTime.getDayOfMonth(),
            localDateTime.getHour(),
            localDateTime.getMinute(),
            localDateTime.getSecond(),
            localDateTime.get( ChronoField.MILLI_OF_SECOND ),
            DatatypeConstants.FIELD_UNDEFINED );
    }
}
