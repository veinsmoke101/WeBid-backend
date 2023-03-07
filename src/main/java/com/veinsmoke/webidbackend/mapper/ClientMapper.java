package com.veinsmoke.webidbackend.mapper;

import com.veinsmoke.webidbackend.dto.RegisterRequest;
import com.veinsmoke.webidbackend.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    
    Client registerRequestToClient(RegisterRequest registerRequest);
}
