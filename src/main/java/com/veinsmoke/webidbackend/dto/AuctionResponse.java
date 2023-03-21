package com.veinsmoke.webidbackend.dto;


import java.time.LocalDateTime;
import java.util.List;


public record AuctionResponse(Long id,
                             String title,
                             String description,
                             LocalDateTime startDate,
                             LocalDateTime endDate,
                             Double startingPrice,
                             Double buyNowPrice,
                             CategoryDto category,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             List<ImageResponse> images,
                             String buyer,
                             String author) {
}
