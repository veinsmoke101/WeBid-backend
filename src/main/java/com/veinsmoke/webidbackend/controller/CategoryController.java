package com.veinsmoke.webidbackend.controller;


import com.veinsmoke.webidbackend.dto.CategoryDto;
import com.veinsmoke.webidbackend.mapper.CategoryMapper;
import com.veinsmoke.webidbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll() {
        return ResponseEntity.ok(
                categoryService.findAll().stream()
                        .map(categoryMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

}
