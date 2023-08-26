package com.veinsmoke.webidbackend.repository;

import com.veinsmoke.webidbackend.model.Auction;
import com.veinsmoke.webidbackend.model.Bid;
import com.veinsmoke.webidbackend.model.Client;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuction(Auction auction, Pageable pageable);
    List<Bid> findByBidder(Client bidder, Pageable pageable);
    Optional<Bid> findFirstByAuctionOrderByBidPriceDesc(Auction auction);
}
