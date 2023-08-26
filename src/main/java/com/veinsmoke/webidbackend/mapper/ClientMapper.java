package com.veinsmoke.webidbackend.mapper;

import com.veinsmoke.webidbackend.dto.BidderDto;
import com.veinsmoke.webidbackend.dto.RegisterRequest;
import com.veinsmoke.webidbackend.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    
    Client registerRequestToClient(RegisterRequest registerRequest);
    String clientToEmail(Client client);
    BidderDto clientToBidder(Client client);
}
