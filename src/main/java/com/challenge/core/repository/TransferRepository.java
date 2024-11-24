package com.challenge.core.repository;

import com.challenge.core.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository  extends JpaRepository<Transfer,Long> {
}
