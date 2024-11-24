package com.challenge.core.repository;

import com.challenge.core.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>{

    @Query("SELECT t FROM Transaction t WHERE t.createdAt BETWEEN :startDate AND :dateTo AND t.cuit = :cuit")
    Page<Transaction> findAllTransactionsByCuil(LocalDateTime startDate, LocalDateTime dateTo, String cuit, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.cuit = :cuit")
    Page<Transaction> findTransactionsByDni(@Param("cuit")String cuit, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.createdAt BETWEEN :startDate AND :dateTo ")
    Page<Transaction> findAllTransactions(LocalDateTime startDate, LocalDateTime dateTo,Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.status.id IN (2, 3)")
    List<Transaction> findTransactionsByPendingOrFailedStatus();

    @Query("SELECT t FROM Transaction t WHERE t.status.description = :description")
    Page<Transaction> findByTransaccionByStatus(@Param("description") String description,Pageable pageable);



    @Query("SELECT t FROM Transaction t WHERE t.createdAt BETWEEN :startDate AND :dateTo" +
            " AND (:description IS NULL OR t.status.description = :description)")
    Page<Transaction> findByTransaccionByStatusTime(
            @Param("description") String description,
            LocalDateTime startDate,
            LocalDateTime dateTo,
            Pageable pageable);




}
