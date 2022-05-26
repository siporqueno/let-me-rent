package ru.letmerent.core.services;

import ru.letmerent.core.entity.Category;

import java.util.Optional;

public interface CategoryService {

    Category findCategoryById(Long id);
    Optional<Category> findCategoryByName(String name);
    Category createCategory(Category category);
}
