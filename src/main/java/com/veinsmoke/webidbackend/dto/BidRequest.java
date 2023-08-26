package com.veinsmoke.webidbackend.dto;

public record BidRequest(
        String email,
        Long auctionId,
        Double bidPrice
) {
}
