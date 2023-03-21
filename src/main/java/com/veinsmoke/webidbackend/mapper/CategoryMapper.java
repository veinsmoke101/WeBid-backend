package com.veinsmoke.webidbackend.mapper;

import com.veinsmoke.webidbackend.dto.CategoryDto;
import com.veinsmoke.webidbackend.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);
}
