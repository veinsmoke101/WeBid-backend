package com.veinsmoke.webidbackend.mapper;


import com.veinsmoke.webidbackend.dto.ImageResponse;
import com.veinsmoke.webidbackend.model.Image;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageResponse imageToImageResponse(Image image);
}
