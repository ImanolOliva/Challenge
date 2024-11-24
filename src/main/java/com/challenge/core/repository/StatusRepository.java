package com.challenge.core.repository;

import com.challenge.core.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StatusRepository extends JpaRepository<Status,Long> {
     Status findByDescription(@Param("description") String description);

}
