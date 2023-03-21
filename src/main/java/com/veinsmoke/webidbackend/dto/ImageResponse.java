package com.veinsmoke.webidbackend.dto;


import lombok.Builder;

@Builder
public record ImageResponse (
        String name
){}