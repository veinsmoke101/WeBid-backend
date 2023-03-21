package com.veinsmoke.webidbackend.controller;


import com.veinsmoke.webidbackend.dto.ImageResponse;
import com.veinsmoke.webidbackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



@RestController("/api/v1/image")
@RequiredArgsConstructor
public class ImageController {
    final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponse> find(@PathVariable final Long id){
        ImageResponse imageResponse = ImageResponse.builder()
                .name(imageService.find(id).getName())
                .build();

        return ResponseEntity.ok(imageResponse);
    }

}
