package com.veinsmoke.webidbackend.repository;

import com.veinsmoke.webidbackend.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
