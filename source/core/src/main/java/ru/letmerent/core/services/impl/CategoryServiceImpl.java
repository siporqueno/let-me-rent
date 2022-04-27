package ru.letmerent.core.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.letmerent.core.entity.Category;
import ru.letmerent.core.repositories.CategoryRepository;
import ru.letmerent.core.services.CategoryService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public Category findCategoryById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return null;
    }

    @Override
    public Category createCategory(Category category) {
        return repository.save(category);
    }
}
