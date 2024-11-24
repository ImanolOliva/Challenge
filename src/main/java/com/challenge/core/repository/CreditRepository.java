package com.challenge.core.repository;

import com.challenge.core.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CreditRepository extends JpaRepository<Credit,Long> {
}
