package com.challenge.core.repository;

import com.challenge.core.entity.PeerToPeer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeerToPeerRepository extends JpaRepository<PeerToPeer,Long> {
}
