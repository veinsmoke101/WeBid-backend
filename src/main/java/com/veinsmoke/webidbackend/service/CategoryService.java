package com.veinsmoke.webidbackend.service;


import com.veinsmoke.webidbackend.model.Category;
import com.veinsmoke.webidbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category save(Category category){
        return categoryRepository.save(category);
    }

    public Optional<Category> findByName(String name){
        return categoryRepository.findByName(name);
    }

}
