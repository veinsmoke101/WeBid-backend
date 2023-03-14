package com.veinsmoke.webidbackend.mapper;

import com.veinsmoke.webidbackend.dto.AuctionRequest;
import com.veinsmoke.webidbackend.model.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuctionMapper {

    @Mapping(target = "category", ignore = true)
    Auction auctionRequestToAuction(AuctionRequest auctionRequest);
}
