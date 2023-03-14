package com.veinsmoke.webidbackend.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


public record AuctionRequest(
        @NotBlank String title,
        @NotBlank String description,
        @FutureOrPresent LocalDateTime startDate,
        @PositiveOrZero Double startingPrice,
        @Positive Double buyNowPrice,
        @NotBlank String category,
        @NotEmpty(message = "One image at least is required") List<MultipartFile> images
){
}