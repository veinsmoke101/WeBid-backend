package com.veinsmoke.webidbackend.dto;


public record BidResponse(
        BidderDto bidder,
        Double bidPrice
) {
}
