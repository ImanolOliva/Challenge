package com.challenge.core.repository;

import com.challenge.core.entity.Transaction;
import com.challenge.core.entity.TransactionLogError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionLogErrorRepository extends JpaRepository<TransactionLogError,Long> {


    @Query("SELECT tle FROM TransactionLogError tle WHERE tle.status.id IN (2, 3)")
    List<TransactionLogError> findTransactionsByPendingOrFailedStatus();

}
