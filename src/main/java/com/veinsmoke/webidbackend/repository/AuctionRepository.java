package com.veinsmoke.webidbackend.repository;

import com.veinsmoke.webidbackend.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
