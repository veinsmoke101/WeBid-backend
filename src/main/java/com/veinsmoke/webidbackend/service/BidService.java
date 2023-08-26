package com.veinsmoke.webidbackend.service;

import com.veinsmoke.webidbackend.model.Auction;
import com.veinsmoke.webidbackend.model.Bid;
import com.veinsmoke.webidbackend.model.Client;
import com.veinsmoke.webidbackend.repository.BidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BidService {

    private final BidRepository bidRepository;

    public Bid save(Auction auction, Client bidder, Double bidPrice) {
        return bidRepository.save(Bid.builder()
                        .auction(auction)
                        .bidder(bidder)
                        .bidPrice(bidPrice)
                        .build()
                );
    }


    public List<Bid> findByAuction(Auction auction, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return bidRepository.findByAuction(auction, pageable);
    }

    public List<Bid> findByBidder(Client bidder, int page, int size ) {
        Pageable pageable = PageRequest.of(page, size);

        return bidRepository.findByBidder(bidder, pageable);
    }

    public List<Bid> findAllByBidder(Client bidder){
        return bidRepository.findByBidder(bidder, Pageable.unpaged());
    }

    public List<Bid> findAllByAuction(Auction auction){
        return bidRepository.findByAuction(auction, Pageable.unpaged());
    }

    public boolean isBidPriceValid(Auction auction, Double bidPrice) {

        Optional<Bid> highestBid = bidRepository.findFirstByAuctionOrderByBidPriceDesc(auction);
        if(highestBid.isEmpty())
            return true;

        return highestBid.get().getBidPrice() < bidPrice;
    }


}
