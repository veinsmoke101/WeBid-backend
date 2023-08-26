package com.veinsmoke.webidbackend.mapper;

import com.veinsmoke.webidbackend.dto.AuctionRequest;
import com.veinsmoke.webidbackend.dto.AuctionResponse;
import com.veinsmoke.webidbackend.model.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ImageMapper.class, ClientMapper.class})
public interface AuctionMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping( target = "images", ignore = true)
    Auction auctionRequestToAuction(AuctionRequest auctionRequest);

    @Mapping( target = "author", source = "auction.author.email")
    AuctionResponse auctionToAuctionResponse(Auction auction);
}
