package com.challenge.core.repository;

import com.challenge.core.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {

     Currency findByDescription(@Param("description") String description);
}
