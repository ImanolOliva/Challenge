package com.challenge.core.repository;

import com.challenge.core.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {

     PaymentMethod findByDescription(@Param("description") String description);

}
