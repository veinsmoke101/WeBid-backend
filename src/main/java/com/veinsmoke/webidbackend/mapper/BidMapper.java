package com.veinsmoke.webidbackend.mapper;

import com.veinsmoke.webidbackend.dto.BidResponse;
import com.veinsmoke.webidbackend.model.Bid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BidMapper {
    BidResponse toBidResponse(Bid bid);
}
