package com.veinsmoke.webidbackend.repository;

import com.veinsmoke.webidbackend.model.SuspendedClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuspendedClientRepository extends JpaRepository<SuspendedClient, Long> {
}
