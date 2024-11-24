package com.challenge.core.service.impl;

import com.challenge.core.controller.response.ResponseMessageDTO;
import com.challenge.core.entity.*;
import com.challenge.core.entity.Currency;
import com.challenge.core.mapper.impl.TransactionMapperImpl;
import com.challenge.core.utils.strategy.Payment;
import com.challenge.core.utils.strategy.PaymentMethodFactory;
import com.challenge.core.controller.response.ResponseTransactionDTO;
import com.challenge.core.utils.strategy.dto.CardDTO;
import com.challenge.core.utils.strategy.dto.PeerToPeerDTO;
import com.challenge.core.utils.strategy.dto.TransferDTO;
import com.challenge.core.repository.*;
import com.challenge.core.controller.request.RequestTransactionDTO;
import com.challenge.core.repository.TransactionLogErrorRepository;
import com.challenge.core.service.TransactionService;
import com.challenge.core.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.challenge.core.utils.IConstanst.*;
import static org.springframework.http.HttpStatus.valueOf;


@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private PaymentMethodServiceImpl paymentMethodServiceImpl;
    @Autowired
    private TransactionLogErrorRepository transactionLogErrorRepository;
    @Autowired
    private CreditRepository creditRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private PeerToPeerRepository peerToPeerRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private  TransactionMapperImpl transactionMapperImpl;
    @Autowired
    private CurrencyServiceImpl currencyService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, PaymentMethodServiceImpl paymentMethodServiceImpl, CurrencyRepository currencyRepository) {
        this.transactionRepository = transactionRepository;
        this.paymentMethodServiceImpl = paymentMethodServiceImpl;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public ResponseEntity<ResponseTransactionDTO> createTransaction(List<RequestTransactionDTO> requestTransactionDTO,Boolean error) {
        List<ResponseTransactionDTO> responseTransactionDTO = new ArrayList();
        try {
            for(RequestTransactionDTO requestDTO: requestTransactionDTO ){
                PaymentMethod paymentMethod = this.getPaymentMethod(requestDTO.getType().toUpperCase());
                Currency currency = this.currencyService.getCurrency(requestDTO.getCurrency());
                responseTransactionDTO.add(this.generateTransaction(requestDTO, paymentMethod, currency,error));
            }
            return new ResponseEntity(responseTransactionDTO,HttpStatusCode.valueOf(200));
        } catch (NullPointerException  e) {
            log.info(e.getMessage());
            return new ResponseEntity("Transactions type is required",valueOf(500));
        } catch (Exception e) {
            return new ResponseEntity("Coins is required",valueOf(500));
        }
    }

    @Override
    public ResponseEntity<ResponseMessageDTO> getTransactionByUser(String cuit, Pageable page, String startDate, String endDate) throws Exception {
        try {
            ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
            Page<Transaction> transactions = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);

            if (startDate != null || endDate != null) {
                LocalDateTime start = LocalDate.parse(startDate, formatter).atStartOfDay();
                LocalDateTime end = LocalDate.parse(endDate, formatter).atStartOfDay();
                PageRequest pageRequest = PageRequest.of(page.getPageNumber(), page.getPageSize());
                transactions = this.transactionRepository.findAllTransactionsByCuil(start, end, cuit, pageRequest);
            } else {
                transactions = this.transactionRepository.findTransactionsByDni(cuit, page);
            }

            responseMessageDTO.setMessage("transactions");
            responseMessageDTO.setData(transactions.getContent());
            return ResponseEntity.ok(responseMessageDTO);
        } catch (Exception e) {
            log.error("Error getting transactions: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    @Override
    public ResponseEntity<ResponseMessageDTO> getStatusTransaction(Long id) throws Exception {
        try{
            Optional<Transaction> value = Optional.ofNullable(this.transactionRepository.findById(id).orElse(null));
            if(value.isEmpty()){
                return new ResponseEntity("Transaction not found",HttpStatus.NO_CONTENT);
            }

            ResponseMessageDTO response = new ResponseMessageDTO();
            response.setMessage("Transactions");
            response.setData(createResponseData(value.get().getStatus().getDescription(),value.get().getCodeTransaction(),value.get().getCuit()));
            return new ResponseEntity(response,HttpStatusCode.valueOf(200));
        }catch (Exception e){
               throw  new Exception(e.getMessage());
        }
    }
    private Map<String, Object> createResponseData(String statusDescription, String codeTransaction,String cuit) {
        Map<String, Object> data = new HashMap<>();
        data.put("status", statusDescription);
        data.put("codeTransaction", codeTransaction);
        data.put("cuil",cuit);
        return data;
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseMessageDTO> reverseTransaction() {
        try {
            List<Transaction> transactions = this.transactionRepository.findTransactionsByPendingOrFailedStatus();
            List<TransactionLogError> transactionErrors = this.transactionLogErrorRepository.findTransactionsByPendingOrFailedStatus();
            if (transactions.isEmpty()) {
                ResponseMessageDTO responseMessage = new ResponseMessageDTO("No pending or failed transactions", null);
                return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
            }

            Status statusApproved = statusRepository.findById(1L)
                    .orElseThrow(() -> new EntityNotFoundException("Status 'Approved' not found"));
            transactions.forEach(transaction -> transaction.setStatus(statusApproved));
            transactionErrors.forEach(error -> error.setStatus(statusApproved));
            this.transactionRepository.saveAll(transactions);
            this.transactionLogErrorRepository.saveAll(transactionErrors);
            ResponseMessageDTO responseMessage = new ResponseMessageDTO("Transactions updated successfully", transactions);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            log.error("Error finding status: {}", e.getMessage());
            ResponseMessageDTO responseMessage = new ResponseMessageDTO("Status 'Approved' not found", null);
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            ResponseMessageDTO responseMessage = new ResponseMessageDTO("Unexpected error.", null);
            return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<?> getAllTransactions(String status, Pageable page, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
        PageRequest pageRequest = PageRequest.of(page.getPageNumber(),page.getPageSize());
        Page<Transaction> transactions =null;
        if(startDate != null || endDate != null){
            LocalDateTime start = LocalDate.parse(startDate,formatter).atStartOfDay();
            LocalDateTime  end = LocalDate.parse(endDate,formatter).atStartOfDay();
             transactions = this.transactionRepository.findAllTransactions(start,end,pageRequest);
            return ResponseEntity.ok(transactions.getContent());
        }

        if (transactions == null || transactions.isEmpty()) {
            transactions = this.transactionRepository.findAll(pageRequest);
            return ResponseEntity.ok(transactions.getContent());
        }
        return ResponseEntity.ok(transactions.getContent());
    }


    @Override
    public ResponseEntity<?> getAllTransactionsByStatus(String description, Pageable page, String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE);
        PageRequest pageRequest = PageRequest.of(page.getPageNumber(),page.getPageSize());
        Page<Transaction> transactions =null;
        if(startDate != null || endDate != null){
            LocalDateTime start = LocalDate.parse(startDate,formatter).atStartOfDay();
            LocalDateTime  end = LocalDate.parse(endDate,formatter).atStartOfDay();
            transactions = this.transactionRepository.findByTransaccionByStatusTime(description,start,end,pageRequest);
            return ResponseEntity.ok(transactions.getContent());
        }
        transactions = this.transactionRepository.findByTransaccionByStatus(description,pageRequest);
        return ResponseEntity.ok(transactions.getContent());
    }

    private PaymentMethod getPaymentMethod(String type) throws Exception {
        try {
           return this.paymentMethodServiceImpl.findByDescription(type.toUpperCase());
        } catch (Exception e) {
            log.info("Error get payment method: {}", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }




    public ResponseTransactionDTO generateTransaction(RequestTransactionDTO requestTransactionDTO,
                                    PaymentMethod paymentMethod,
                                    Currency currency,Boolean error) throws Exception {
        Transaction transaction = new Transaction();
        String paymentType = requestTransactionDTO.getType().toUpperCase();
        Payment paymentDataMapper = PaymentMethodFactory.getMapper(paymentType);
        Object paymentData = paymentDataMapper.mapData(requestTransactionDTO);
        return this.saveTransaction(transaction, paymentData,currency,paymentMethod,error);
    }

    private ResponseTransactionDTO saveTransaction(Transaction transaction, Object paymentData,Currency currency,PaymentMethod paymentMethod,Boolean error) throws Exception {
        try {

            if (paymentData instanceof CardDTO) {
               return saveCreditTransaction(transaction, (CardDTO) paymentData,currency,paymentMethod,error);
            } else if (paymentData instanceof TransferDTO) {
                return saveTransferTransaction(transaction, (TransferDTO) paymentData,currency,paymentMethod,error);
            } else if (paymentData instanceof PeerToPeerDTO) {
                return savePeerToPeerTransaction(transaction, (PeerToPeerDTO) paymentData,currency,paymentMethod,error);
            } else {
                throw new IllegalArgumentException("Unsupported payment data type");
            }
        } catch (Exception e) {
            log.info(UNSUPPORT, e.getMessage());
            throw  new Exception(e.getMessage());
        }
    }

    @Transactional
    private ResponseTransactionDTO saveCreditTransaction(Transaction transaction, CardDTO paymentData,
                                                         Currency currency,PaymentMethod paymentMethod,Boolean error) throws Exception {
        Transaction t = this.utils.setCommonTransactionFields(transaction, paymentData, currency, paymentMethod);
        try{
            if(error){
                return this.saveCreditTransactionError(t,paymentData,currency,paymentMethod);
            }
         Credit credit = transactionMapperImpl.INSTANCE.toCredit(t,paymentData.getMccCode(),paymentData.getMerchant());
         creditRepository.save(credit);
         return buildResponse(t,paymentData.getAmount(),paymentData.getCuil(),COMPLETED,t.getCurrency().getSimbol(),OK);
        }catch (ConstraintViolationException  e){
            log.info("Error transaction peer to peer: {}", e.getMessage());
            return buildResponse(t,paymentData.getAmount(),paymentData.getCuil(),ERROR,currency.getSimbol(),e.getConstraintViolations().iterator().next().getPropertyPath().toString().concat(e.getConstraintViolations().iterator().next().getMessage()));
        }catch (Exception e){
            log.info("Unexpected error: ", e.getMessage());
            return buildResponse(t,paymentData.getAmount(),paymentData.getCuil(),ERROR,currency.getSimbol(),e.getMessage());
        }
    }

    @Transactional
    private ResponseTransactionDTO saveTransferTransaction(Transaction transaction, TransferDTO paymentData,
                                         Currency currency,PaymentMethod paymentMethod,Boolean error )  throws Exception{
        Transaction t = this.utils.setCommonTransactionFields(transaction, paymentData, currency, paymentMethod);
        try {
            if(error){
               return  this.saveTransferTransactionError(t,paymentData,currency,paymentMethod);
            }
            Transfer  transfer = transactionMapperImpl.INSTANCE.toTransfer(t,paymentData.getBank_code(),paymentData.getRecipientAccount());
            transferRepository.save(transfer);
            return buildResponse(t,paymentData.getAmount(),paymentData.getCuil(),COMPLETED,currency.getSimbol(),OK);
        }catch (ConstraintViolationException  e){
            log.info("error transaction peer to peer: {}", e.getMessage());
            return buildResponse(t,paymentData.getAmount(),paymentData.cuil(),ERROR,currency.getSimbol(),e.getConstraintViolations().iterator().next().getPropertyPath().toString().concat(e.getConstraintViolations().iterator().next().getMessage()));
        }catch (Exception e){
            log.info("Unexpected error: {}", e.getMessage());
            return buildResponse(t,paymentData.getAmount(),paymentData.cuil(),ERROR,currency.getSimbol(),e.getMessage());
        }
    }



    @Transactional
    private ResponseTransactionDTO savePeerToPeerTransaction(Transaction transaction, PeerToPeerDTO paymentData,
                                           Currency currency,PaymentMethod paymentMethod,Boolean error ) throws  Exception {
        Transaction t = this.utils.setCommonTransactionFields(transaction, paymentData, currency, paymentMethod);
      try{
          if(error){
              return this.savePeerToPeerTransactionError(t,paymentData,currency,paymentMethod);
          }
          PeerToPeer peerToPeer = transactionMapperImpl.INSTANCE.toPeerToPeer(t,paymentData.getSenderId(),paymentData.getRecipientId(),paymentData.getNote());
          peerToPeerRepository.save(peerToPeer);
          return buildResponse(t,paymentData.getAmount(),paymentData.getCuil(),COMPLETED,currency.getSimbol(),OK);
      }catch (ConstraintViolationException  e){
          log.info("Error transaction peer to peer: {}", e.getMessage());
          return buildResponse(t,paymentData.getAmount(),paymentData.cuil(),ERROR,currency.getSimbol(), e.getConstraintViolations().iterator().next().getPropertyPath().toString().concat(e.getConstraintViolations().iterator().next().getMessage()));
      }catch (Exception e){
          log.info("Unexpected error: {}", e.getMessage());
          return buildResponse(t,paymentData.getAmount(),paymentData.cuil(),ERROR,currency.getSimbol(),e.getMessage());
      }
    }


    private ResponseTransactionDTO buildResponse(Transaction t, BigDecimal amount, String dni,String status,String symbol,String message){
        return transactionMapperImpl.toTransaction(t, amount,dni,status,symbol,message);
    }


    @Transactional
    private ResponseTransactionDTO savePeerToPeerTransactionError(Transaction transaction, PeerToPeerDTO paymentData,
                                                             Currency currency,PaymentMethod paymentMethod ) throws  Exception {
        TransactionLogError transactionLogError = new TransactionLogError();
        PeerToPeer peerToPeer = new PeerToPeer();
        Transaction t = this.utils.setCommonTransactionFields(transaction, paymentData, currency, paymentMethod);
        try{
            peerToPeer = transactionMapperImpl.INSTANCE.toPeerToPeer(t,paymentData.getSenderId(),paymentData.getRecipientId(),paymentData.getNote());
            throw new Exception();
        }catch (Exception e){
            peerToPeer.setStatus(utils.getStatus(ERROR));
            peerToPeerRepository.save(peerToPeer);
            transactionLogError = transactionMapperImpl.INSTANCE.toTransactionLogError(peerToPeer);
            transactionLogError.setCuit(paymentData.getCuil());
            transactionLogErrorRepository.save(transactionLogError);
            log.info("Unexpected error: {}", e.getMessage());
            return buildResponse(t,paymentData.getAmount(),paymentData.cuil(),ERROR,currency.getSimbol(),"ERROR GENERATE PEER TO PEER");
        }
    }

    @Transactional
    private ResponseTransactionDTO saveTransferTransactionError(Transaction transaction, TransferDTO paymentData,
                                                           Currency currency,PaymentMethod paymentMethod )  throws Exception{
        TransactionLogError transactionLogError = new TransactionLogError();
        Transfer transfer = new Transfer();
        Transaction t = this.utils.setCommonTransactionFields(transaction, paymentData, currency, paymentMethod);
        try {
            transfer = transactionMapperImpl.INSTANCE.toTransfer(t,paymentData.getBank_code(),paymentData.getRecipientAccount());
            throw  new Exception();
        }catch (Exception e){
            transfer.setStatus(utils.getStatus(ERROR));
            transferRepository.save(transfer);
            transactionLogError = transactionMapperImpl.INSTANCE.toTransactionLogError(transfer);
            transactionLogError.setCuit(paymentData.getCuil());
            transactionLogErrorRepository.save(transactionLogError);
            return buildResponse(t,paymentData.getAmount(),paymentData.cuil(),ERROR,currency.getSimbol(),"ERROR GENERATE TRANSFER");
        }
    }
    @Transactional
    private ResponseTransactionDTO saveCreditTransactionError(Transaction transaction, CardDTO paymentData,
                                                         Currency currency,PaymentMethod paymentMethod) throws Exception {
        TransactionLogError transactionLogError = new TransactionLogError();
        Transaction t = this.utils.setCommonTransactionFields(transaction, paymentData, currency, paymentMethod);
        Credit credit = new Credit();
        try{
            credit = transactionMapperImpl.INSTANCE.toCredit(t,paymentData.getMccCode(),paymentData.getMerchant());
            throw new Exception();
        }catch (Exception e){
            credit.setStatus(utils.getStatus(ERROR));
            creditRepository.save(credit);
            transactionLogError = transactionMapperImpl.INSTANCE.toTransactionLogError(credit);
            transactionLogError.setCuit(paymentData.getCuil());
            transactionLogErrorRepository.save(transactionLogError);
            log.info("Error transaction peer to peer: {}", e.getMessage());
            return buildResponse(t,paymentData.getAmount(),paymentData.cuil(),ERROR,currency.getSimbol(),"ERROR GENERATE CREDIT TRANSACTION");
        }
    }


}
